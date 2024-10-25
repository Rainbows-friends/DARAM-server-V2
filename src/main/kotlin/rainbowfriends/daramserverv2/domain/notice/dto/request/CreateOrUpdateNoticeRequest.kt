package rainbowfriends.daramserverv2.domain.notice.dto.request

import jakarta.validation.constraints.NotBlank

data class CreateOrUpdateNoticeRequest(
    @field:NotBlank val title: String,
    @field:NotBlank val content: String
)