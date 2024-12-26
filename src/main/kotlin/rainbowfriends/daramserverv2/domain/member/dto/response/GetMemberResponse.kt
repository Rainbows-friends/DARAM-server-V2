package rainbowfriends.daramserverv2.domain.member.dto.response

import rainbowfriends.daramserverv2.global.member.enums.Roles

data class GetMemberResponse(  // Member 조회 응답 DTO
    val id: Long?,
    val name: String?,
    val grade: Int?,
    val classNum: Int?,
    val number: Int?,
    val floor: Int?,
    val room: Int?,
    val role: Roles?,
    val stay: Boolean?,
    val lateNumber: Long?
)