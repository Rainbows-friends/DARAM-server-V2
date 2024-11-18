package rainbowfriends.daramserverv2.domain.member.component

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.global.member.entity.MemberElasticsearch
import rainbowfriends.daramserverv2.global.member.repository.MemberElasticsearchRepository

@Component
class MemberFindbyElasticsearch(private val memberElasticsearchRepository: MemberElasticsearchRepository) {
    @Cacheable(value = ["allMembers"], key = "'allMembers'")
    fun findMemberByElasticsearch(): List<MemberElasticsearch> {
        if (memberElasticsearchRepository.count() <= 0) {
            throw MemberNotFoundException("Elasticsearch is empty")
        }
        return memberElasticsearchRepository.findAll().toList()
    }
}