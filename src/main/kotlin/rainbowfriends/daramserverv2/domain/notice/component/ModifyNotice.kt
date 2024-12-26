package rainbowfriends.daramserverv2.domain.notice.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.notice.dto.request.PatchNoticeRequest
import rainbowfriends.daramserverv2.domain.notice.dto.response.NoticeResponse
import rainbowfriends.daramserverv2.domain.notice.exception.NoticeNotFoundException
import rainbowfriends.daramserverv2.domain.notice.repository.NoticeRepository

@Component
class ModifyNotice(private val noticeRepository: NoticeRepository) {
    fun modifyNotice(id: Long, request: PatchNoticeRequest): NoticeResponse {
        val notice = noticeRepository.findById(id).orElseThrow {  // 해당 ID의 공지사항이 존재하지 않을 경우
            throw NoticeNotFoundException("Notice Not Found")
        }
        request.title?.let { notice.title = it }  // title 값이 null이 아닐 경우 notice.title에 할당
        request.content?.let { notice.context = it }  // content 값이 null이 아닐 경우 notice.context에 할당
        request.author?.let { notice.author = it }  // author 값이 null이 아닐 경우 notice.author에 할당
        noticeRepository.save(notice)  // 공지사항 수정
        return NoticeResponse(  // NoticeResponse 객체 반환
            id = notice.id,
            title = notice.title,
            context = notice.context,
            author = notice.author,
            createdAt = notice.createdAt
        )
    }
}