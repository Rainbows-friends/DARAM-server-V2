package rainbowfriends.daramserverv2.global.member.component

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.data.domain.PageRequest
import org.springframework.retry.backoff.ExponentialBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberElasticsearchRepository
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository

@Component
class MemberDataSync(
    private val memberRepository: MemberRepository,
    private val memberElasticsearchRepository: MemberElasticsearchRepository
) {
    private val batchSize = 100

    private val retryTemplate: RetryTemplate = RetryTemplate().apply {
        setRetryPolicy(SimpleRetryPolicy(3))
        setBackOffPolicy(ExponentialBackOffPolicy().apply {
            initialInterval = 1000
            maxInterval = 10000
            multiplier = 2.0
        })
    }

    fun syncMemberToElasticsearch(member: Member) {
        retryTemplate.execute<Void, Exception> {
            memberElasticsearchRepository.save(member.toElasticsearchDocument())
            null
        }
    }

    fun deleteMemberFromElasticsearch(member: Member) {
        retryTemplate.execute<Void, Exception> {
            memberElasticsearchRepository.delete(member.toElasticsearchDocument())
            null
        }
    }

    @EventListener(ApplicationReadyEvent::class)
    fun syncMembersOnStartup() {
        retryTemplate.execute<Void, Exception> {
            if (memberElasticsearchRepository.count() > 0) {
                memberElasticsearchRepository.deleteAll()
            }
            var page = 0
            var members: List<Member>
            do {
                val pageable = PageRequest.of(page, batchSize)
                members = memberRepository.findAll(pageable).content
                if (members.isNotEmpty()) {
                    retryTemplate.execute<Void, Exception> {
                        val documents = members.map { it.toElasticsearchDocument() }
                        memberElasticsearchRepository.saveAll(documents)
                        null
                    }
                }
                page++
            } while (members.size == batchSize)
            null
        }
    }
}