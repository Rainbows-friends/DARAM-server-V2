package rainbowfriends.daramserverv2.domain.checkin.dto.response

import rainbowfriends.daramserverv2.global.member.dto.MemberDTO
import java.time.LocalDate

data class GetCheckInResponse(
    val id: Long?,  // CheckIn ID
    val user: MemberDTO,  // CheckIn한 Member
    var checkinStatus: Boolean,  // CheckIn 여부
    val checkinDate: String?  // CheckIn 시각
)