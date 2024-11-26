package rainbowfriends.daramserverv2.domain.notice.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.notice.component.FindNotice
import rainbowfriends.daramserverv2.domain.notice.dto.response.NoticeResponse
import rainbowfriends.daramserverv2.domain.notice.service.GetNoticeService

@Service
class GetNoticeServiceImpl(private val findNotice: FindNotice) : GetNoticeService {
    override fun getNotice(id: Long): NoticeResponse = findNotice.findNotice(id)
}