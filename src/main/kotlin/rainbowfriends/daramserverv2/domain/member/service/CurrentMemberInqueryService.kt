package rainbowfriends.daramserverv2.domain.member.service

import jakarta.servlet.http.HttpServletRequest
import rainbowfriends.daramserverv2.global.member.dto.MemberDTO

interface CurrentMemberInqueryService {
    fun getCurrentMember(request: HttpServletRequest): MemberDTO
}