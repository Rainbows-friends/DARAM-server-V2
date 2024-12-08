package rainbowfriends.daramserverv2.domain.checkin.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.checkin.component.CheckInMemberQuery
import rainbowfriends.daramserverv2.domain.checkin.dto.enums.GetCheckInComponentAction
import rainbowfriends.daramserverv2.domain.checkin.dto.response.GetCheckInResponse
import rainbowfriends.daramserverv2.domain.checkin.service.CheckedInMemberService
import rainbowfriends.daramserverv2.global.checkin.entity.CheckInMongoDB
import rainbowfriends.daramserverv2.global.member.entity.Member
import java.time.LocalDate

@Service
class CheckedInMemberServiceImpl(
    private val checkInMemberQuery: CheckInMemberQuery
) : CheckedInMemberService {
    override fun getCheckedInMember(): List<GetCheckInResponse> {
        val checkInData: List<CheckInMongoDB> = checkInMemberQuery.getCheckInMemberWithLock(
            GetCheckInComponentAction.GET_CHECKED_IN_MEMBER,
            LocalDate.now()
        )
        return checkInData.map { mongoCheckIn ->
            val memberInfo: Member = checkInMemberQuery.getMemberInfo(mongoCheckIn.studentId)
            GetCheckInResponse(
                id = mongoCheckIn.id!!.toLong(),
                user = memberInfo,
                checkinStatus = mongoCheckIn.checkinStatus,
                checkinDate = mongoCheckIn.checkinDate
            )
        }
    }
}