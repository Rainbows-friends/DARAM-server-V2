package rainbowfriends.daramserverv2.domain.notice.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.notice.exception.NoticeNotFoundException
import rainbowfriends.daramserverv2.domain.notice.repository.NoticeRepository

@Component
class DeleteNotice(private val noticeRepository: NoticeRepository) {
    fun deleteNotice(id: Long) {  // 공지사항 삭제
        if (!noticeRepository.existsById(id)) {  // 해당 ID의 공지사항이 존재하지 않을 경우
            throw NoticeNotFoundException("Notice with ID $id not found")  // 예외 처리
        }
        noticeRepository.deleteById(id)  // 공지사항 삭제
    }
}