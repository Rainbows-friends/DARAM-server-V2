package rainbowfriends.daramserverv2.domain.checkin.dto.response

import rainbowfriends.daramserverv2.global.member.entity.Member
import java.time.LocalDate

data class GetCheckInResponse(
    val id: Long?,
    val user: Member,
    var checkinStatus: Boolean,
    val checkinDate: LocalDate
)