package rainbowfriends.daramserverv2.global.checkin.component

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import java.time.DayOfWeek
import java.time.LocalDate

@Component
class CheckInDataScheduler(
    private val checkInRepository: CheckInRepository,
    private val lateNumberUpdater: LateNumberUpdater,
    private val checkInPreparation: CheckInPreparation
) {

    @EventListener(ApplicationReadyEvent::class)
    fun initializeCheckInData() {
        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)
        checkInPreparation.prepareCheckInsForDate(today)
        checkInPreparation.prepareCheckInsForDate(tomorrow)
    }

    @Scheduled(cron = "0 35 21 * * *")
    fun deleteOldCheckInData() {
        val cutoffDate = LocalDate.now().minusDays(2)
        checkInRepository.deleteAll(checkInRepository.findByCheckinInfoDate(cutoffDate))
    }

    @Scheduled(cron = "0 30 21 * * *")
    fun scheduledCheckInDataSync() {
        val today: LocalDate = LocalDate.now()
        val tomorrow: LocalDate = today.plusDays(1)
        if (today.dayOfWeek == DayOfWeek.THURSDAY || today.dayOfWeek == DayOfWeek.FRIDAY) {
            return
        }
        checkInPreparation.prepareCheckInsForDate(tomorrow)
        lateNumberUpdater.lateNumberRaise(today)
    }
}