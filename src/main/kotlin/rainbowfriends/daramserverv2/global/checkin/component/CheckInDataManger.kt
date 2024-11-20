package rainbowfriends.daramserverv2.global.checkin.component

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.cache.config.CacheConfig
import rainbowfriends.daramserverv2.global.checkin.event.CheckInDataSyncCompletedEvent
import rainbowfriends.daramserverv2.global.checkin.exception.LateNumberRaiseFailException
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInMongoDBRepository
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository
import java.time.DayOfWeek
import java.time.LocalDate

@Component
class CheckInDataManger(
    private val checkInRepository: CheckInRepository,
    private val checkInMongoDBRepository: CheckInMongoDBRepository,
    private val memberRepository: MemberRepository,
    private val checkInDataSync: CheckInDataSync,
    private val cacheConfig: CacheConfig,
    private val checkInPreparation: CheckInPreparation
) {

    @EventListener(CheckInDataSyncCompletedEvent::class)
    fun initializeCheckInData() {
        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)
        checkInPreparation.prepareCheckInsForDate(today)
        checkInPreparation.prepareCheckInsForDate(tomorrow)
    }

    @Scheduled(cron = "0 30 21 * * *")
    fun scheduledCheckInDataSync() {
        val today: LocalDate = LocalDate.now()
        val tomorrow: LocalDate = today.plusDays(1)
        println(tomorrow)
        if (today.dayOfWeek == DayOfWeek.FRIDAY) {
            return
        }
        checkInPreparation.prepareCheckInsForDate(tomorrow)
        lateNumberRaise(today)
    }

    private fun lateNumberRaise(today: LocalDate) {
        try {
            checkInRepository.findByCheckinDate(today).forEach { checkIn ->
                if (checkIn.checkinStatus == false) {
                    val member: Member = memberRepository.findById(checkIn.id!!).get()
                    member.lateNumber.plus(1)
                    memberRepository.save(member)
                }
            }
        } catch (e: Exception) {
            throw LateNumberRaiseFailException("Late Number Raise failed")
        }
    }
}