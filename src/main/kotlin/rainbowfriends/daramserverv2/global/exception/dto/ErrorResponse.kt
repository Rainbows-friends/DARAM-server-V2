package rainbowfriends.daramserverv2.global.exception.dto

import rainbowfriends.daramserverv2.global.exception.dto.enums.ErrorStatus

data class ErrorResponse(
    val status: ErrorStatus,
    val message: String,
    val code: Int
)