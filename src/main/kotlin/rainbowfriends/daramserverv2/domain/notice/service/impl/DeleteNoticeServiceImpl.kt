package rainbowfriends.daramserverv2.domain.notice.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.notice.component.DeleteNotice
import rainbowfriends.daramserverv2.domain.notice.service.DeleteNoticeService

@Service
class DeleteNoticeServiceImpl(private val deleteNotice: DeleteNotice) : DeleteNoticeService {
    override fun deleteNotice(id: Long) {
        deleteNotice.deleteNotice(id)
    }
}