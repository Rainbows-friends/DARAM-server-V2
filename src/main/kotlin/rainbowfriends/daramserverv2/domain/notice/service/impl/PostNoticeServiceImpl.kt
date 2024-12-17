package rainbowfriends.daramserverv2.domain.notice.service.impl

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.notice.component.SaveNotice
import rainbowfriends.daramserverv2.domain.notice.dto.request.PostNoticeRequest
import rainbowfriends.daramserverv2.domain.notice.dto.response.NoticeResponse
import rainbowfriends.daramserverv2.domain.notice.service.PostNoticeService
import rainbowfriends.daramserverv2.global.security.jwt.service.impl.JwtTokenParserServiceImpl

@Service
class PostNoticeServiceImpl(
    private val jwtTokenParserServiceImpl: JwtTokenParserServiceImpl,
    private val saveNotice: SaveNotice
) :
    PostNoticeService {
    override fun postNotice(request: HttpServletRequest, requestDto: PostNoticeRequest): NoticeResponse {
        val role = jwtTokenParserServiceImpl.extractRole(request.getHeader("Authorization").substringAfter("Bearer "))
        return saveNotice.saveNotice(requestDto, role)
    }
}