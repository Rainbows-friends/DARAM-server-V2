package rainbowfriends.daramserverv2.domain.checkin.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import java.time.LocalDate

@Service
class CheckInTransactionService(
    private val checkInRepository: CheckInRepository,
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun getMemberInfo(studentId: Short): Member {
        studentId.toString().let {
            val grade = it[0].toString().toInt()
            val classNum = it[1].toString().toInt()
            val studentNum = it.substring(2).toInt()
            return memberRepository.findByGradeAndClassNumAndNumber(grade, classNum, studentNum)
                ?: throw MemberNotFoundException("Member not found")
        }
    }

    @Transactional
    fun getCheckInRecord(user: Member, date: LocalDate): CheckIn {
        return checkInRepository.findByUserAndCheckinDate(user, date)
            ?: throw IllegalStateException("Check-in record not found")
    }

    @Transactional
    fun toggleCheckInStatus(checkIn: CheckIn) {
        checkIn.checkinStatus = !checkIn.checkinStatus
        checkInRepository.save(checkIn)
    }
}
