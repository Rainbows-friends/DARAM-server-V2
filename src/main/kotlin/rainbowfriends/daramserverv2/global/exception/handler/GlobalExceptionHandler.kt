package rainbowfriends.daramserverv2.global.exception.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import rainbowfriends.daramserverv2.domain.auth.exception.NoTokenException
import rainbowfriends.daramserverv2.domain.auth.exception.TokenNotFoundException
import rainbowfriends.daramserverv2.domain.member.exception.InvalidStudentIdException
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.domain.notice.exception.DuplicateNoticeException
import rainbowfriends.daramserverv2.global.checkin.exception.CheckInStatusSwitchException
import rainbowfriends.daramserverv2.global.checkin.exception.DateCalculationException
import rainbowfriends.daramserverv2.global.checkin.exception.LateNumberRaiseFailException
import rainbowfriends.daramserverv2.global.exception.dto.ErrorResponse
import rainbowfriends.daramserverv2.global.exception.dto.enums.ErrorStatus
import rainbowfriends.daramserverv2.global.security.exception.TokenFormatException
import java.security.InvalidKeyException

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(TokenFormatException::class)
    fun handleTokenFormatException(ex: TokenFormatException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),
            message = "Token format is invalid",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(NoTokenException::class)
    fun handleNoTokenException(ex: NoTokenException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),
            message = "Token is not provided",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(TokenNotFoundException::class)
    fun handleTokenNotFoundException(ex: TokenNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),
            message = "Token is not found",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(DuplicateNoticeException::class)
    fun handleDuplicateNoticeException(ex: DuplicateNoticeException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.BAD_REQUEST.value(),
            message = "Notice is already exist",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidKeyException::class)
    fun handleInvalidKeyException(ex: InvalidKeyException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),
            message = "Invalid key",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(MemberNotFoundException::class)
    fun handleMemberNotFoundException(ex: MemberNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.NOT_FOUND.value(),
            message = "Member is not found",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(DateCalculationException::class)
    fun handleDateCalculationException(ex: DateCalculationException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Date calculation failed",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(InvalidStudentIdException::class)
    fun handleInvalidStudentIdException(ex: InvalidStudentIdException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Invalid student id",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(LateNumberRaiseFailException::class)
    fun handleLateNumberRaiseFailException(ex: LateNumberRaiseFailException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Late number raise fail",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(CheckInStatusSwitchException::class)
    fun handleCheckInStatusSwitchException(ex: CheckInStatusSwitchException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "CheckIn status switch failed",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}