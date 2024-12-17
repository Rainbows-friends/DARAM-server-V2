package rainbowfriends.daramserverv2.domain.checkin.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.checkin.component.CheckInMemberQuery
import rainbowfriends.daramserverv2.domain.checkin.dto.enums.GetCheckInComponentAction
import rainbowfriends.daramserverv2.domain.checkin.dto.response.GetCheckInResponse
import rainbowfriends.daramserverv2.domain.checkin.service.MissedCheckInMemberService
import rainbowfriends.daramserverv2.global.checkin.dto.CheckInDTO
import java.time.LocalDate

@Service
class MissedCheckInMemberServiceImpl(private val checkInMemberQuery: CheckInMemberQuery) : MissedCheckInMemberService {
    override fun getMissedCheckInMember(): List<GetCheckInResponse> {
        val checkInData: List<CheckInDTO> = checkInMemberQuery.getCheckInMember(
            GetCheckInComponentAction.GET_MISSED_CHECK_IN_MEMBER,
            LocalDate.now()
        ).map {
            CheckInDTO(
                id = it.id,
                user = it.user,
                checkinStatus = it.checkinStatus,
                checkinDate = it.checkinDate
            )
        }
        return checkInData.map { CheckIn ->
            GetCheckInResponse(
                id = CheckIn.id!!.toLong(),
                user = CheckIn.user.toDto(),
                checkinStatus = CheckIn.checkinStatus,
                checkinDate = null
            )
        }
    }
}