package rainbowfriends.daramserverv2.domain.notice.service

import jakarta.servlet.http.HttpServletRequest
import rainbowfriends.daramserverv2.domain.notice.dto.request.CreateOrUpdateNoticeRequest
import rainbowfriends.daramserverv2.domain.notice.entity.Notice

interface PostNoticeService {
    fun postNotice(request: HttpServletRequest, requestDto: CreateOrUpdateNoticeRequest): Notice
}