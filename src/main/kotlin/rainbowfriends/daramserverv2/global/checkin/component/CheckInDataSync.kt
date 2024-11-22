package rainbowfriends.daramserverv2.global.checkin.component

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.data.domain.PageRequest
import org.springframework.retry.backoff.ExponentialBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.checkin.dto.CheckInDTO
import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn
import rainbowfriends.daramserverv2.global.checkin.event.CheckInDataSyncCompletedEvent
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInMongoDBRepository
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import rainbowfriends.daramserverv2.global.event.ApplicationContextProvider

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
            val checkInDTO = checkIn.toDTO()
            checkInDTO.toMongoDBDocument().let { mongoDocument ->
                checkInMongoDBRepository.save(mongoDocument)
            }
            null
        }
    }

    fun deleteCheckInFromMongoDB(checkIn: CheckIn) {
        retryTemplate.execute<Void, Exception> {
            val checkInDTO = checkIn.toDTO()
            checkInDTO.toMongoDBDocument().let { mongoDocument ->
                checkInMongoDBRepository.delete(mongoDocument)
            }
            null
        }
    }

    @EventListener(ApplicationReadyEvent::class)
    fun syncCheckInOnStartUp() {
        retryTemplate.execute<Void, Exception> {
            checkInMongoDBRepository.deleteAll()
            var page = 0
            var checkIns: List<CheckIn>
            do {
                val pageable = PageRequest.of(page, batchSize)
                checkIns = checkInRepository.findAll(pageable).content
                if (checkIns.isNotEmpty()) {
                    val checkInDTOs = checkIns.map {
                        CheckInDTO(
                            id = it.id,
                            userName = it.user.name,
                            studentId = it.user.generateStudentId(
                                it.user.grade,
                                it.user.classNum,
                                it.user.number
                            ),
                            checkinStatus = it.checkinStatus,
                            checkinDate = it.checkinDate
                        )
                    }
                    retryTemplate.execute<Void, Exception> {
                        val documents = checkInDTOs.map { it.toMongoDBDocument() }
                        checkInMongoDBRepository.saveAll(documents)
                        null
                    }
                }
                page++
            } while (checkIns.isNotEmpty())
            null
        }
        ApplicationContextProvider.context.publishEvent(CheckInDataSyncCompletedEvent())
    }
}