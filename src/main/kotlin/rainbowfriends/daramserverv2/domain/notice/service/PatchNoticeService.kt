package rainbowfriends.daramserverv2.domain.notice.service

import rainbowfriends.daramserverv2.domain.notice.dto.request.PatchNoticeRequest
import rainbowfriends.daramserverv2.domain.notice.dto.response.NoticeResponse

interface PatchNoticeService {
    fun patchNotice(id: Long, request: PatchNoticeRequest): NoticeResponse
}