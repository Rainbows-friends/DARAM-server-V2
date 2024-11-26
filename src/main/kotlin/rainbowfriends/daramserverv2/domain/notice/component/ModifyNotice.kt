package rainbowfriends.daramserverv2.domain.notice.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.notice.dto.request.PatchNoticeRequest
import rainbowfriends.daramserverv2.domain.notice.dto.response.NoticeResponse
import rainbowfriends.daramserverv2.domain.notice.exception.NoticeNotFoundException
import rainbowfriends.daramserverv2.domain.notice.repository.NoticeRepository

@Component
class ModifyNotice(
    private val noticeRepository: NoticeRepository
) {
    fun modifyNotice(id: Long, request: PatchNoticeRequest): NoticeResponse {
        val notice = noticeRepository.findById(id).orElseThrow {
            throw NoticeNotFoundException("Notice Not Found")
        }
        request.title?.let { notice.title = it }
        request.content?.let { notice.context = it }
        request.author?.let { notice.author = it }
        noticeRepository.save(notice)
        return NoticeResponse(
            id = notice.id,
            title = notice.title,
            context = notice.context,
            author = notice.author,
            createdAt = notice.createdAt
        )
    }
}