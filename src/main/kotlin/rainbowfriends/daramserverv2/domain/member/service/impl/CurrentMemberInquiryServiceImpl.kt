package rainbowfriends.daramserverv2.domain.member.service.impl

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.domain.member.exception.TokenNotFoundException
import rainbowfriends.daramserverv2.domain.member.service.CurrentMemberInquiryService
import rainbowfriends.daramserverv2.global.member.component.FindMember
import rainbowfriends.daramserverv2.global.member.dto.MemberDTO
import rainbowfriends.daramserverv2.global.security.jwt.service.JwtTokenParserService

@Service
class CurrentMemberInquiryServiceImpl(
    private val jwtTokenParserService: JwtTokenParserService,
    private val findMember: FindMember
) : CurrentMemberInquiryService {
    override fun getCurrentMember(request: HttpServletRequest): MemberDTO {
        if (request.getHeader("Authorization") == null) {  // Authorization 헤더가 없을 경우 예외 발생
            throw TokenNotFoundException("Token not found")
        } else  // Authorization 헤더가 있을 경우
            return findMember.findMemberByEmail(  // Email로 Member 조회
                jwtTokenParserService.extractUserId(  // JWT 토큰에서 UserId 추출
                    request.getHeader("Authorization").substring(7)  // Authorization 헤더에서 Bearer 제외
                )
            )
                ?.toDto() ?: throw MemberNotFoundException("Member not found")  // Member가 없을 경우 예외 발생
    }
}