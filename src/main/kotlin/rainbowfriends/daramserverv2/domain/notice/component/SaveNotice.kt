package rainbowfriends.daramserverv2.domain.notice.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.notice.dto.request.PostNoticeRequest
import rainbowfriends.daramserverv2.domain.notice.dto.response.NoticeResponse
import rainbowfriends.daramserverv2.domain.notice.entity.Notice
import rainbowfriends.daramserverv2.domain.notice.exception.DuplicateNoticeException
import rainbowfriends.daramserverv2.domain.notice.repository.NoticeRepository
import rainbowfriends.daramserverv2.global.member.enums.Roles
import java.time.LocalDateTime

@Component
class SaveNotice(private val noticeRepository: NoticeRepository) {
    fun saveNotice(request: PostNoticeRequest, role: Roles): NoticeResponse {
        val notice = Notice(
            id = null,
            title = request.title,
            context = request.content,
            author = role,
            createdAt = LocalDateTime.now()
        )
        noticeRepository.findByTitle(request.title)?.let {  // 해당 title의 공지사항이 존재할 경우
            if (it.author == role && it.context == request.content) {  // author와 context가 모두 같을 경우
                throw DuplicateNoticeException("notice already exists")  // 예외 처리
            }
        }
        return noticeRepository.save(notice).let {  // NoticeResponse 객체 반환
            NoticeResponse(  // NoticeResponse 객체 반환
                id = it.id,
                title = it.title,
                context = it.context,
                author = it.author,
                createdAt = it.createdAt
            )
        }
    }
}