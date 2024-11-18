package rainbowfriends.daramserverv2.global.checkin.component

import org.springframework.cache.Cache
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.cache.config.CacheConfig
import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn
import rainbowfriends.daramserverv2.global.checkin.exception.DateCalculationException
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInMongoDBRepository
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import rainbowfriends.daramserverv2.global.member.component.MemberDataSync
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository
import java.time.DayOfWeek
import java.time.LocalDate

@Component
class CheckInDataManger(
    private val checkInRepository: CheckInRepository,
    private val checkInMongoDBRepository: CheckInMongoDBRepository,
    private val memberRepository: MemberRepository,
    private val memberDataSync: MemberDataSync,
    private val checkInDataSync: CheckInDataSync,
    private val cacheConfig: CacheConfig
) {
    @Scheduled(cron = "0 30 21 * * *")
    fun scheduledCheckInDataSync() { // TODO lateNumber를 증분하는 로직 추가 필요
        val today: LocalDate? = LocalDate.now()
        val tomorrow: LocalDate? = today?.plusDays(1)
        println(tomorrow)
        if (today == null || tomorrow == null) {
            throw DateCalculationException("Date calculation failed")
        }
        if (today.dayOfWeek == DayOfWeek.FRIDAY) {
            return
        }
        val cache: Cache? = cacheConfig.cacheManager().getCache("allMembers")
        val allMember: List<Any?> = cache?.get("allMembers")?.get() as? List<*> ?: memberRepository.findAll()
        if (checkInMongoDBRepository.findByCheckinDate(tomorrow).isEmpty()) {
            allMember.forEach {
                if (it is Member) {
                    val checkIn = CheckIn(
                        id = null,
                        user = it,
                        checkinDate = tomorrow,
                        checkinStatus = false
                    )
                    checkInRepository.save(checkIn)
                    checkInDataSync.syncCheckInToMongoDB(checkIn)
                }
            }
        }
    }
}