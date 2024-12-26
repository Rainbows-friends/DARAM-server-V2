package rainbowfriends.daramserverv2.domain.checkin.dto.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

class CheckInStatusSwitchRequest {
    @field:NotNull
    @field:Min(1101)
    @field:Max(3418)
    val studentId: Short? = null
}