package rainbowfriends.daramserverv2.domain.auth.dto.request

import jakarta.validation.constraints.NotBlank

data class CameraAuthorizationRequest(  // 카메라 인증 요청 DTO
    @field:NotBlank val key: String  // key를 받아서 카메라 인증 요청(null 또는 빈 문자열이면 안됨)
)