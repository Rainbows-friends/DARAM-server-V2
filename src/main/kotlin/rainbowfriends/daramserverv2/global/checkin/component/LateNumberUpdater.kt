package rainbowfriends.daramserverv2.global.checkin.component

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.global.checkin.exception.LateNumberRaiseFailException
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository
import java.time.LocalDate

@Component
class LateNumberUpdater(
    private val checkInRepository: CheckInRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun lateNumberRaise(date: LocalDate) {
        try {
            checkInRepository.findByCheckinDate(date).forEach { checkIn ->
                if (!checkIn.checkinStatus) {
                    val member = memberRepository.findById(checkIn.id!!)
                        .orElseThrow { MemberNotFoundException("Member not found") }
                    member.lateNumber += 1
                    memberRepository.save(member)
                }
            }
        } catch (e: Exception) {
            throw LateNumberRaiseFailException("Late Number Raise failed")
        }
    }
}