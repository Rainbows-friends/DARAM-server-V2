package rainbowfriends.daramserverv2.domain.checkin.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.checkin.component.CheckInMemberQuery
import rainbowfriends.daramserverv2.domain.checkin.dto.enums.GetCheckInComponentAction
import rainbowfriends.daramserverv2.domain.checkin.dto.response.GetCheckInResponse
import rainbowfriends.daramserverv2.domain.checkin.service.CheckedInMemberService
import rainbowfriends.daramserverv2.global.checkin.entity.CheckInMongoDB
import rainbowfriends.daramserverv2.global.member.component.FindMember
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.util.ParseStudentId.parseStudentId
import java.time.LocalDate

@Service
class CheckedInMemberServiceImpl(
    private val checkInMemberQuery: CheckInMemberQuery,
    private val findMember: FindMember
) : CheckedInMemberService {
    override fun getCheckedInMember(): List<GetCheckInResponse> {
        val checkInData: List<CheckInMongoDB> = checkInMemberQuery.getCheckInMember(GetCheckInComponentAction.GET_CHECKED_IN_MEMBER, LocalDate.now())
        return checkInData.map { mongoCheckIn ->
            val (grade, classNum, number) = parseStudentId(mongoCheckIn.studentId.toString())
            val memberInfo: Member = findMember.findMemberByGradeAndClassNumAndNumber(grade, classNum, number)!!
            GetCheckInResponse(
                id = mongoCheckIn.id?.toLong(),
                user = memberInfo,
                checkinStatus = mongoCheckIn.checkinStatus,
                checkinDate = mongoCheckIn.checkinDate
            )
        }
    }
}