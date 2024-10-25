package rainbowfriends.daramserverv2.domain.time.dto.response

import java.time.LocalDateTime

data class DetailedTimeResponse(
    val limitTime: LocalDateTime,
    val hours: Long,
    val minutes: Long,
    val seconds: Long
)