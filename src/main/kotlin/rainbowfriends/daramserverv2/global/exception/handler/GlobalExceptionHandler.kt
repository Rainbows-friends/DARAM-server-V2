package rainbowfriends.daramserverv2.global.exception.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import rainbowfriends.daramserverv2.global.exception.InvalidKeyException
import rainbowfriends.daramserverv2.global.exception.TokenFormatException
import rainbowfriends.daramserverv2.global.exception.dto.ErrorResponse
import rainbowfriends.daramserverv2.global.exception.dto.enums.ErrorStatus

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(InvalidKeyException::class)
    fun handleInvalilKeyException(ex: InvalidKeyException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),
            message = "Invalid Key",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(TokenFormatException::class)
    fun handleTokenFormatException(ex: TokenFormatException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.BAD_REQUEST.value(),
            message = "Token format is invalid",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }
}