package rainbowfriends.daramserverv2.global.exception.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import rainbowfriends.daramserverv2.domain.auth.exception.*
import rainbowfriends.daramserverv2.domain.member.exception.InvalidStudentIdException
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.domain.notice.exception.DuplicateNoticeException
import rainbowfriends.daramserverv2.domain.notice.exception.NoticeNotFoundException
import rainbowfriends.daramserverv2.domain.notice.exception.PatchNoticeRequestException
import rainbowfriends.daramserverv2.global.checkin.exception.CheckInStatusSwitchException
import rainbowfriends.daramserverv2.global.checkin.exception.DateCalculationException
import rainbowfriends.daramserverv2.global.checkin.exception.LateNumberRaiseFailException
import rainbowfriends.daramserverv2.global.exception.dto.ErrorResponse
import rainbowfriends.daramserverv2.global.exception.dto.enums.ErrorStatus
import rainbowfriends.daramserverv2.global.security.exception.*
import java.security.InvalidKeyException

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(InvalidTokenFormatException::class)
    fun handleInvalidTokenFormatException(ex: InvalidTokenFormatException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),
            message = "Token format is invalid",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(ExpiredTokenException::class)
    fun handleExpiredTokenException(ex: ExpiredTokenException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),
            message = "Token is expired",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(ExpiredRefreshTokenException::class)
    fun handleExpiredRefreshTokenException(ex: ExpiredRefreshTokenException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),
            message = "Refresh Token is expired",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(InvalidTokenException::class)
    fun handleInvalidTokenException(ex: InvalidTokenException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),
            message = "Token is invalid",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(InvalidRefreshTokenException::class)
    fun handleInvalidRefreshTokenException(ex: InvalidRefreshTokenException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),
            message = "Refresh Token is invalid",
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

    @ExceptionHandler(PatchNoticeRequestException::class)
    fun handlePatchNoticeRequestException(ex: PatchNoticeRequestException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.BAD_REQUEST.value(),
            message = "Patch Notice Request is invalid",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NoticeNotFoundException::class)
    fun handleNoticeNotFoundException(ex: NoticeNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.NOT_FOUND.value(),
            message = "Notice is not found",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(EmailFormatException::class)
    fun handleEmailFormatException(ex: EmailFormatException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.BAD_REQUEST.value(),
            message = "Email format is invalid",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidCodeException::class)
    fun handleInvalidCodeException(ex: InvalidCodeException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.BAD_REQUEST.value(),
            message = "Invalid code",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ReissueTokenException::class)
    fun handleReissueTokenException(ex: ReissueTokenException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),
            message = "Reissue Token is invalid",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(RegenerateTokenFailedException::class)
    fun handleRegenerateTokenFailedException(ex: RegenerateTokenFailedException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Regenerate Failed",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(KeyNotFoundException::class)
    fun handleKeyNotFoundException(ex: KeyNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),
            message = "Key is invalid",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }
}