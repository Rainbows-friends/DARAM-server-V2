package rainbowfriends.daramserverv2.domain.notice.dto.request

import jakarta.validation.constraints.NotBlank

data class PostNoticeRequest(  // 공지사항 작성 요청 DTO
    @field:NotBlank val title: String,  // title 값이 null이 아니고 띄어쓰기가 없어야 함
    @field:NotBlank val content: String   // content 값이 null이 아니고 띄어쓰기가 없어야 함
)