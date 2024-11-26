package rainbowfriends.daramserverv2.domain.notice.service

import rainbowfriends.daramserverv2.domain.notice.dto.response.NoticeResponse

interface GetAllNoticeService {
    fun getAllNotices(): List<NoticeResponse>
}