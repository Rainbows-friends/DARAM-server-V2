package rainbowfriends.daramserverv2.domain.time.dto.response

import java.time.LocalDateTime

data class SecondsResponse(  // 초 단위로 시간을 표현하는 응답 DTO
    val limitTime: LocalDateTime,
    val seconds: Long
)