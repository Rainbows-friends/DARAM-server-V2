package rainbowfriends.daramserverv2.domain.notice.dto.request

import rainbowfriends.daramserverv2.global.member.enums.Roles

data class PatchNoticeRequest(  // 공지사항 수정 요청 DTO
    val title: String? = null,
    val content: String? = null,
    val author: Roles? = null
)