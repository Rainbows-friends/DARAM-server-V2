package rainbowfriends.daramserverv2.domain.checkin.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInMongoDBRepository
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository
import rainbowfriends.daramserverv2.global.member.dto.MemberDTO
import java.time.LocalDate

@Service
class CheckInTransaction(
    private val checkInRepository: CheckInRepository,
    private val checkInMongoDBRepository: CheckInMongoDBRepository,
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun getMemberDto(grade: Int, classNum: Int, studentNum: Int): MemberDTO {
        return memberRepository.findByGradeAndClassNumAndNumber(grade, classNum, studentNum)!!.toDto()
    }

    @Transactional(readOnly = true)
    fun getCheckInRecord(user: MemberDTO, date: LocalDate): CheckIn {
        val user: Member? = memberRepository.findByGradeAndClassNumAndNumber(user.grade, user.classNum, user.number)
        return checkInRepository.findByUserAndCheckinDate(user!!, date)
    }

    @Transactional
    fun toggleCheckInStatus(checkIn: CheckIn) {
        checkIn.checkinStatus = !checkIn.checkinStatus
        checkInRepository.save(checkIn)
    }
}