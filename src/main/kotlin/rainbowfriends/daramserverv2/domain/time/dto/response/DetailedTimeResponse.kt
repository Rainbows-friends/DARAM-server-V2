package rainbowfriends.daramserverv2.domain.time.dto.response

import java.time.LocalDateTime

data class DetailedTimeResponse(  // 시간을 상세하게 표현하는 응답 DTO
    val limitTime: LocalDateTime,
    val hours: Long,
    val minutes: Long,
    val seconds: Long
)