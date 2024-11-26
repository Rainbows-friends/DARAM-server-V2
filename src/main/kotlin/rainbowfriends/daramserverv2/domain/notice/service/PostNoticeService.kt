package rainbowfriends.daramserverv2.domain.notice.service

import jakarta.servlet.http.HttpServletRequest
import rainbowfriends.daramserverv2.domain.notice.dto.request.PostNoticeRequest
import rainbowfriends.daramserverv2.domain.notice.dto.response.NoticeResponse

interface PostNoticeService {
    fun postNotice(request: HttpServletRequest, requestDto: PostNoticeRequest): NoticeResponse
}