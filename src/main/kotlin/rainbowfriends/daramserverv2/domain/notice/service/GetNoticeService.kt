package rainbowfriends.daramserverv2.domain.notice.service

import rainbowfriends.daramserverv2.domain.notice.dto.response.NoticeResponse

interface GetNoticeService {
    fun getNotice(id: Long): NoticeResponse
}