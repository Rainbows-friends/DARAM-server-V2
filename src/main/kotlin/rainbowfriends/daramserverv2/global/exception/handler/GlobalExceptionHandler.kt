package rainbowfriends.daramserverv2.global.exception.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import rainbowfriends.daramserverv2.domain.auth.exception.EmailFormatException
import rainbowfriends.daramserverv2.domain.auth.exception.InvalidCodeException
import rainbowfriends.daramserverv2.domain.auth.exception.KeyNotFoundException
import rainbowfriends.daramserverv2.domain.auth.exception.ReissueTokenException
import rainbowfriends.daramserverv2.domain.member.exception.InvalidStudentIdException
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.domain.member.exception.PatchRoomException
import rainbowfriends.daramserverv2.domain.member.exception.TokenNotFoundException
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

@ControllerAdvice  // Controller에서 발생하는 예외를 처리하기 위한 어노테이션
class GlobalExceptionHandler {  // 전역 예외 처리 클래스
    @ExceptionHandler(InvalidTokenFormatException::class)  // InvalidTokenFormatException 예외 처리
    fun handleInvalidTokenFormatException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),  // HTTP 상태 코드 401
            message = "Token format is invalid",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(ExpiredTokenException::class)  // ExpiredTokenException 예외 처리
    fun handleExpiredTokenException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),  // HTTP 상태 코드 401
            message = "Token is expired",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(ExpiredRefreshTokenException::class)  // ExpiredRefreshTokenException 예외 처리
    fun handleExpiredRefreshTokenException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),  // HTTP 상태 코드 401
            message = "Refresh Token is expired",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(InvalidTokenException::class)  // InvalidTokenException 예외 처리
    fun handleInvalidTokenException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),  // HTTP 상태 코드 401
            message = "Token is invalid",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(InvalidRefreshTokenException::class)  // InvalidRefreshTokenException 예외 처리
    fun handleInvalidRefreshTokenException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),  // HTTP 상태 코드 401
            message = "Refresh Token is invalid",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(DuplicateNoticeException::class)  // DuplicateNoticeException 예외 처리
    fun handleDuplicateNoticeException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.BAD_REQUEST.value(),  // HTTP 상태 코드 400
            message = "Notice is already exist",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidKeyException::class)  // InvalidKeyException 예외 처리
    fun handleInvalidKeyException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),  // HTTP 상태 코드 401
            message = "Invalid key",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(MemberNotFoundException::class)  // MemberNotFoundException 예외 처리
    fun handleMemberNotFoundException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.NOT_FOUND.value(),  // HTTP 상태 코드 404
            message = "Member is not found",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(DateCalculationException::class)  // DateCalculationException 예외 처리
    fun handleDateCalculationException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),  // HTTP 상태 코드 500
            message = "Date calculation failed",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(InvalidStudentIdException::class)  // InvalidStudentIdException 예외 처리
    fun handleInvalidStudentIdException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),  // HTTP 상태 코드 500
            message = "Invalid student id",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(LateNumberRaiseFailException::class)  // LateNumberRaiseFailException 예외 처리
    fun handleLateNumberRaiseFailException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),  // HTTP 상태 코드 500
            message = "Late number raise fail",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(CheckInStatusSwitchException::class)  // CheckInStatusSwitchException 예외 처리
    fun handleCheckInStatusSwitchException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),  // HTTP 상태 코드 500
            message = "CheckIn status switch failed",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(PatchNoticeRequestException::class)  // PatchNoticeRequestException 예외 처리
    fun handlePatchNoticeRequestException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.BAD_REQUEST.value(),  // HTTP 상태 코드 400
            message = "Patch Notice Request is invalid",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NoticeNotFoundException::class)  // NoticeNotFoundException 예외 처리
    fun handleNoticeNotFoundException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.NOT_FOUND.value(),  // HTTP 상태 코드 404
            message = "Notice is not found",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(EmailFormatException::class)  // EmailFormatException 예외 처리
    fun handleEmailFormatException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.BAD_REQUEST.value(),  // HTTP 상태 코드 400
            message = "Email format is invalid",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidCodeException::class)  // InvalidCodeException 예외 처리
    fun handleInvalidCodeException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.BAD_REQUEST.value(),  // HTTP 상태 코드 400
            message = "Invalid code",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ReissueTokenException::class)  // ReissueTokenException 예외 처리
    fun handleReissueTokenException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),  // HTTP 상태 코드 401
            message = "Reissue Token is invalid",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(RegenerateTokenFailedException::class)  // RegenerateTokenFailedException 예외 처리
    fun handleRegenerateTokenFailedException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),  // HTTP 상태 코드 500
            message = "Regenerate Failed",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(KeyNotFoundException::class)  // KeyNotFoundException 예외 처리
    fun handleKeyNotFoundException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.UNAUTHORIZED.value(),  // HTTP 상태 코드 401
            message = "Key is invalid",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(TokenNotFoundException::class)  // TokenNotFoundException 예외 처리
    fun handleTokenNotFoundException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.BAD_REQUEST.value(),  // HTTP 상태 코드 400
            message = "Token is not found",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(PatchRoomException::class)  // PatchRoomException 예외 처리
    fun handlePatchRoomException(): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),  // HTTP 상태 코드 500
            message = "Patch Room failed",
            status = ErrorStatus.ERROR
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}