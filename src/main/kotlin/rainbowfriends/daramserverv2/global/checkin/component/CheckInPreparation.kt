package rainbowfriends.daramserverv2.global.checkin.component

import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository
import java.time.LocalDate

@Component
class CheckInPreparation(
    private val checkInRepository: CheckInRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun prepareCheckInsForDate(date: LocalDate) {
        val allMember: List<Member> = memberRepository.findAll()
        if (checkInRepository.findByCheckinInfoDate(date).isEmpty()) {
            val checkIns: List<CheckIn> = allMember.map { member ->
                CheckIn(
                    id = null,
                    user = member,
                    checkinInfoDate = date,
                    checkinStatus = false
                )
            }
            checkInRepository.saveAll(checkIns)
        }
    }
}