package rainbowfriends.daramserverv2.domain.notice.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.notice.dto.request.CreateOrUpdateNoticeRequest
import rainbowfriends.daramserverv2.domain.notice.entity.Notice
import rainbowfriends.daramserverv2.domain.notice.exception.DuplicateNoticeException
import rainbowfriends.daramserverv2.domain.notice.repository.NoticeRepository
import rainbowfriends.daramserverv2.global.member.enums.Roles
import rainbowfriends.daramserverv2.global.security.token.TokenService
import java.time.LocalDateTime

@Component
class SaveNotice(
    private val noticeRepository: NoticeRepository, private val tokenService: TokenService
) {
    fun saveNotice(request: CreateOrUpdateNoticeRequest, role: Roles): Notice {
        val notice = Notice(
            id = null,
            title = request.title,
            context = request.content,
            author = role,
            createdAt = LocalDateTime.now()
        )
        noticeRepository.findByTitle(request.title)?.let {
            if (it.author == role && it.context == request.content) {
                throw DuplicateNoticeException("notice already exists")
            }
        }
        return noticeRepository.save(notice)
    }
}