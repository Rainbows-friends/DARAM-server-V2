package rainbowfriends.daramserverv2.domain.checkin.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.checkin.component.CheckInMemberQuery
import rainbowfriends.daramserverv2.domain.checkin.dto.GetCheckInComponentAction
import rainbowfriends.daramserverv2.domain.checkin.service.MissedCheckInMemberService
import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn
import rainbowfriends.daramserverv2.global.checkin.entity.CheckInMongoDB
import rainbowfriends.daramserverv2.global.member.entity.Member
import java.time.LocalDate

@Service
class MissedCheckInMemberServiceImpl(private val checkInMemberQuery: CheckInMemberQuery) : MissedCheckInMemberService {
    override fun getMissedCheckInMember(): List<CheckIn> {
        val checkInData: List<CheckInMongoDB> = checkInMemberQuery.getCheckInMember(
            GetCheckInComponentAction.GET_MISSED_CHECK_IN_MEMBER,
            LocalDate.now()
        )
        return checkInData.map { mongoCheckIn ->
            val memberInfo: Member = checkInMemberQuery.getMemberInfo(mongoCheckIn.studentId)
            CheckIn(
                id = mongoCheckIn.id!!.toLong(),
                user = memberInfo,
                checkinStatus = mongoCheckIn.checkinStatus,
                checkinDate = mongoCheckIn.checkinDate
            )
        }
    }
}