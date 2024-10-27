package rainbowfriends.daramserverv2.domain.time.dto.response

import java.time.LocalDateTime

data class SecondsResponse(
    val limitTime: LocalDateTime,
    val seconds: Long
)