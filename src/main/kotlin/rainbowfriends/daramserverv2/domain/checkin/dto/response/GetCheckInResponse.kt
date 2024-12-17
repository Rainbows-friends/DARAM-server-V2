package rainbowfriends.daramserverv2.domain.checkin.dto.response

import rainbowfriends.daramserverv2.global.member.dto.MemberDTO
import java.time.LocalDate

data class GetCheckInResponse(
    val id: Long?,
    val user: MemberDTO,
    var checkinStatus: Boolean,
    val checkinDate: String?
)