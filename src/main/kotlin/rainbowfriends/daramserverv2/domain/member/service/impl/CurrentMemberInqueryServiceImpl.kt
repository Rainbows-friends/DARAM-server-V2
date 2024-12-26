package rainbowfriends.daramserverv2.domain.member.service.impl

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.domain.member.exception.TokenNotFoundException
import rainbowfriends.daramserverv2.domain.member.service.CurrentMemberInqueryService
import rainbowfriends.daramserverv2.global.member.component.FindMember
import rainbowfriends.daramserverv2.global.member.dto.MemberDTO
import rainbowfriends.daramserverv2.global.security.jwt.service.JwtTokenParserService

@Service
class CurrentMemberInqueryServiceImpl(
    private val jwtTokenParserService: JwtTokenParserService,
    private val findMember: FindMember
) : CurrentMemberInqueryService {
    override fun getCurrentMember(request: HttpServletRequest): MemberDTO {
        if (request.getHeader("Authorization") == null) {
            throw TokenNotFoundException("Token not found")
        } else
            return findMember.findMemberByEmail(
                jwtTokenParserService.extractUserId(
                    request.getHeader("Authorization").substring(7)
                )
            )
                ?.toDto() ?: throw MemberNotFoundException("Member not found")
    }
}