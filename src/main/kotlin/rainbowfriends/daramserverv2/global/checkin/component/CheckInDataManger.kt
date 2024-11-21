package rainbowfriends.daramserverv2.global.checkin.component

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.checkin.event.CheckInDataSyncCompletedEvent
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInMongoDBRepository
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import java.time.DayOfWeek
import java.time.LocalDate

@Component
class CheckInDataManger(
    private val checkInRepository: CheckInRepository,
    private val checkInMongoDBRepository: CheckInMongoDBRepository,
    private val lateNumberRaise: LateNumberRaise,
    private val checkInPreparation: CheckInPreparation
) {

    @EventListener(CheckInDataSyncCompletedEvent::class)
    fun initializeCheckInData() {
        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)
        checkInPreparation.prepareCheckInsForDate(today)
        checkInPreparation.prepareCheckInsForDate(tomorrow)
    }

    @Scheduled(cron = "0 0 23 * * *")
    fun deleteOldCheckInData() {
        val cutoffDate = LocalDate.now().minusDays(2)
        checkInRepository.deleteAll(checkInRepository.findByCheckinDate(cutoffDate))
        checkInMongoDBRepository.deleteAll(checkInMongoDBRepository.findByCheckinDate(cutoffDate))
    }

    @Scheduled(cron = "0 30 21 * * *")
    fun scheduledCheckInDataSync() {
        val today: LocalDate = LocalDate.now()
        val tomorrow: LocalDate = today.plusDays(1)
        if (today.dayOfWeek == DayOfWeek.FRIDAY) {
            return
        }
        checkInPreparation.prepareCheckInsForDate(tomorrow)
        lateNumberRaise.lateNumberRaise(today)
    }
}