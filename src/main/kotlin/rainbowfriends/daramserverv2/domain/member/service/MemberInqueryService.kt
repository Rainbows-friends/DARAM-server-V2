package rainbowfriends.daramserverv2.domain.member.service

import rainbowfriends.daramserverv2.domain.member.dto.response.GetMemberResponse

interface MemberInqueryService {
    fun getAllMember(
        id: Long?,
        stay: Boolean?,
        floor: Int?,
        room: Int?,
        grade: Int?,
        classNum: Int?
    ): List<GetMemberResponse>
}