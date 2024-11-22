package rainbowfriends.daramserverv2.domain.checkin.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.checkin.dto.GetCheckInComponentAction
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.global.checkin.entity.CheckInMongoDB
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInMongoDBRepository
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository
import java.time.LocalDate

@Component
class CheckInMemberQuery(
    private val checkInMongoDBRepository: CheckInMongoDBRepository,
    private val memberRepository: MemberRepository
) {
    fun getCheckInMember(action: GetCheckInComponentAction, date: LocalDate): List<CheckInMongoDB> {
        if (action == GetCheckInComponentAction.GET_CHECKED_IN_MEMBER) {
            return checkInMongoDBRepository.findByCheckinDateAndCheckinStatus(date, true)
        } else if (action == GetCheckInComponentAction.GET_MISSED_CHECK_IN_MEMBER) {
            return checkInMongoDBRepository.findByCheckinDateAndCheckinStatus(date, false)
        }
        return checkInMongoDBRepository.findAll()
    }

    fun getMemberInfo(studentId: Short): Member {
        studentId.toString().let {
            val grade = it[0].toString().toInt()
            val classNum = it[1].toString().toInt()
            val studentNum = it.substring(2).toInt()
            return memberRepository.findByGradeAndClassNumAndNumber(grade, classNum, studentNum)
                ?: throw MemberNotFoundException("Member not found")
        }
    }
}