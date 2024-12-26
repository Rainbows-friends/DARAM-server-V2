package rainbowfriends.daramserverv2.global.exception.dto

import rainbowfriends.daramserverv2.global.exception.dto.enums.ErrorStatus

data class ErrorResponse(  // 에러 응답 DTO
    val status: ErrorStatus,
    val message: String,
    val code: Int
)