package rainbowfriends.daramserverv2.domain.auth.dto.request

import jakarta.validation.constraints.NotBlank

data class CameraAuthorizationRequest(
    @field:NotBlank val key: String
)