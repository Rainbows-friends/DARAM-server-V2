package rainbowfriends.daramserverv2.domain.checkin.dto.request

import jakarta.validation.constraints.NotNull

class CheckInStatusSwitchRequest {
    @field:NotNull val name: String? = null
}