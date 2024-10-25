package rainbowfriends.daramserverv2.domain.time.dto.response

import java.time.LocalDateTime

data class TimeFormattedResponse(
    val limitTime: LocalDateTime,
    val remainTime: String
)