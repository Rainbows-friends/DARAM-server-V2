package rainbowfriends.daramserverv2.domain.checkin.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.checkin.component.CheckInDataSync
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository
import rainbowfriends.daramserverv2.global.redis.redisson.util.DistributedLock
import java.time.LocalDate

@Component
class CheckInStatusSwitch(
    private val checkInRepository: CheckInRepository,
    private val memberRepository: MemberRepository,
    private val checkInDataSync: CheckInDataSync,
    private val distributedLock: DistributedLock
) {

    fun switchCheckInStatus(username: String?, date: LocalDate): Boolean {
        if (username == null) return false
        val lockKey = "check-in:$date"
        return distributedLock.executeWithLock(lockKey, 5L, 10L) {
            try {
                val user: Member = memberRepository.findByName(username) ?: return@executeWithLock false
                val checkIn = checkInRepository.findByUserAndCheckinDate(user, date)
                checkIn.checkinStatus = !checkIn.checkinStatus
                checkInRepository.save(checkIn)
                checkInDataSync.syncCheckInToMongoDB(checkIn)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}