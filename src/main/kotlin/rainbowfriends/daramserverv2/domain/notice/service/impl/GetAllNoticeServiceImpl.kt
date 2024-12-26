package rainbowfriends.daramserverv2.domain.notice.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.notice.component.FindNotice
import rainbowfriends.daramserverv2.domain.notice.dto.response.NoticeResponse
import rainbowfriends.daramserverv2.domain.notice.service.GetAllNoticeService

@Service
class GetAllNoticeServiceImpl(private val findNotice: FindNotice) : GetAllNoticeService {
    override fun getAllNotices(): List<NoticeResponse> {
        val allNotice: List<NoticeResponse> = findNotice.findNotice()  // FindNotice 컴포넌트의 findNotice 메서드 호출
        return allNotice.map {  // NoticeResponse 객체로 변환
            NoticeResponse(
                id = it.id,
                title = it.title,
                context = it.context,
                author = it.author,
                createdAt = it.createdAt
            )
        }
    }
}