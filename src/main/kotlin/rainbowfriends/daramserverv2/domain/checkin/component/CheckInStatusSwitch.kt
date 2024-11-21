package rainbowfriends.daramserverv2.domain.checkin.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.checkin.component.CheckInDataSync
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository
import java.time.LocalDate

@Component
class CheckInStatusSwitch(
    private val checkInRepository: CheckInRepository,
    private val memberRepository: MemberRepository,
    private val checkInDataSync: CheckInDataSync
) {
    fun switchCheckInStatus(username: String?, date: LocalDate): Boolean {
        try {
            if (username == null) return false
            val user: Member = memberRepository.findByName(username) ?: return false
            val checkIn = checkInRepository.findByUserAndCheckinDate(user, date)
            checkIn.checkinStatus = !checkIn.checkinStatus
            checkInRepository.save(checkIn)
            checkInDataSync.syncCheckInToMongoDB(checkIn)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}