package rainbowfriends.daramserverv2.global.checkin.dto

import rainbowfriends.daramserverv2.global.member.entity.Member
import java.time.LocalDate
import java.time.LocalDateTime

data class CheckInDTO(  // CheckIn Entity의 복제본인 DTO
    val id: Long?,
    val user: Member,
    var checkinStatus: Boolean = false,
    val checkinDate: LocalDateTime? = null
)