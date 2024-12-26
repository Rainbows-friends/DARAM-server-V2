package rainbowfriends.daramserverv2.domain.notice.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.notice.dto.response.NoticeResponse
import rainbowfriends.daramserverv2.domain.notice.exception.NoticeNotFoundException
import rainbowfriends.daramserverv2.domain.notice.repository.NoticeRepository

@Component
class FindNotice(private val noticeRepository: NoticeRepository) {
    fun findNotice(id: Long): NoticeResponse {
        val notice = noticeRepository.findById(id).orElseThrow {  // 해당 ID의 공지사항이 존재하지 않을 경우
            NoticeNotFoundException("Notice Not Found")
        }
        return NoticeResponse(  // NoticeResponse 객체 반환
            id = notice.id,
            title = notice.title,
            context = notice.context,
            author = notice.author,
            createdAt = notice.createdAt
        )
    }

    fun findNotice(): List<NoticeResponse> {  // 오버로딩
        val notices = noticeRepository.findAll()  // 모든 공지사항 조회
        if (notices.isEmpty()) {  // 공지사항이 존재하지 않을 경우(=notices List가 비어있을 경우)
            throw NoticeNotFoundException("Notice Not Found")
        }
        return notices.map {  // NoticeResponse 객체로 변환
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