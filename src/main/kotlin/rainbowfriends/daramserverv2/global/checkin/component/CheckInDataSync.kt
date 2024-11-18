package rainbowfriends.daramserverv2.global.checkin.component

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.data.domain.PageRequest
import org.springframework.retry.backoff.ExponentialBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInMongoDBRepository
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository

@Component
class CheckInDataSync(
    private val checkInRepository: CheckInRepository,
    private val checkInMongoDBRepository: CheckInMongoDBRepository
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

    fun syncCheckInToMongoDB(checkIn: CheckIn) {
        retryTemplate.execute<Void, Exception> {
            checkInMongoDBRepository.save(checkIn.toMongoDBDocument())
            null
        }
    }

    fun deleteCheckInFromMongoDB(checkIn: CheckIn) {
        retryTemplate.execute<Void, Exception> {
            checkInMongoDBRepository.delete(checkIn.toMongoDBDocument())
            null
        }
    }

    @EventListener(ApplicationReadyEvent::class)
    fun syncCheckInOnStartUp() {
        retryTemplate.execute<Void, Exception> {
            if (checkInMongoDBRepository.count() > 0) {
                checkInMongoDBRepository.deleteAll()
            }
            var page = 0
            var checkIns: List<CheckIn>
            do {
                val pageable = PageRequest.of(page, batchSize)
                checkIns = checkInRepository.findAll(pageable).content
                if (checkIns.isNotEmpty()) {
                    retryTemplate.execute<Void, Exception> {
                        val documents = checkIns.map { it.toMongoDBDocument() }
                        checkInMongoDBRepository.saveAll(documents)
                        null
                    }
                }
                page++
            } while (checkIns.isNotEmpty())
            null
        }
    }
}