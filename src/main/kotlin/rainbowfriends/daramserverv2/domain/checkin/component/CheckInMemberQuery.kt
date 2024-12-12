package rainbowfriends.daramserverv2.domain.checkin.component

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import rainbowfriends.daramserverv2.domain.checkin.dto.enums.GetCheckInComponentAction
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.global.checkin.entity.CheckInMongoDB
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInMongoDBRepository
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository
import rainbowfriends.daramserverv2.global.redis.redisson.util.DistributedLock
import java.time.LocalDate

@Component
class CheckInMemberQuery(
    private val checkInMongoDBRepository: CheckInMongoDBRepository,
    private val memberRepository: MemberRepository,
    private val distributedLock: DistributedLock
) {

    fun getCheckInMemberWithLock(action: GetCheckInComponentAction, date: LocalDate): List<CheckInMongoDB> {
        val lockKey = "check-in-lock:$date"
        return distributedLock.executeWithLock(lockKey, 5L, 10L) {
            getCheckInMember(action, date)
        }
    }

    private fun getCheckInMember(action: GetCheckInComponentAction, date: LocalDate): List<CheckInMongoDB> =
        when (action) {
            GetCheckInComponentAction.GET_CHECKED_IN_MEMBER -> getCheckInMember(date)
            GetCheckInComponentAction.GET_MISSED_CHECK_IN_MEMBER -> getMissedCheckInMember(date)
        }

    private fun getMissedCheckInMember(date: LocalDate): List<CheckInMongoDB> {
        return checkInMongoDBRepository.findByCheckinDateAndCheckinStatus(date, false)
    }

    private fun getCheckInMember(date: LocalDate): List<CheckInMongoDB> {
        return checkInMongoDBRepository.findByCheckinDateAndCheckinStatus(date, true)
    }

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
}