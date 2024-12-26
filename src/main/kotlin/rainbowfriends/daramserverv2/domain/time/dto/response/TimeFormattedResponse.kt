package rainbowfriends.daramserverv2.domain.time.dto.response

import java.time.LocalDateTime

data class TimeFormattedResponse(  // 시간을 포맷팅하여 표현하는 응답 DTO
    val limitTime: LocalDateTime,
    val remainTime: String
)