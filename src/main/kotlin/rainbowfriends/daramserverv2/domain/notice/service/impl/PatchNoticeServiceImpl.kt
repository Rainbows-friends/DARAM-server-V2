package rainbowfriends.daramserverv2.domain.notice.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.notice.component.ModifyNotice
import rainbowfriends.daramserverv2.domain.notice.dto.request.PatchNoticeRequest
import rainbowfriends.daramserverv2.domain.notice.dto.response.NoticeResponse
import rainbowfriends.daramserverv2.domain.notice.exception.PatchNoticeRequestException
import rainbowfriends.daramserverv2.domain.notice.service.PatchNoticeService
import rainbowfriends.daramserverv2.global.member.enums.Roles

@Service
class PatchNoticeServiceImpl(private val modifyNotice: ModifyNotice) : PatchNoticeService {
    override fun patchNotice(id: Long, request: PatchNoticeRequest): NoticeResponse {
        if (request.author == Roles.USER) {
            throw PatchNoticeRequestException("User cannot modify notice")
        }
        if (request.title == null && request.content == null && request.author == null) {
            throw PatchNoticeRequestException("At least one field should be modified")
        }
        return modifyNotice.modifyNotice(id, request)
    }
}