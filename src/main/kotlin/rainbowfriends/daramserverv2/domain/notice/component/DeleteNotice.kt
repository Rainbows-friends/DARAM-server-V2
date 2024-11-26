package rainbowfriends.daramserverv2.domain.notice.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.notice.exception.NoticeNotFoundException
import rainbowfriends.daramserverv2.domain.notice.repository.NoticeRepository

@Component
class DeleteNotice(private val noticeRepository: NoticeRepository) {
    fun deleteNotice(id: Long) {
        if (!noticeRepository.existsById(id)) {
            throw NoticeNotFoundException("Notice with ID $id not found")
        }
        noticeRepository.deleteById(id)
    }
}