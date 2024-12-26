package rainbowfriends.daramserverv2.domain.checkin.dto.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

class CheckInStatusSwitchRequest {
    @field:NotNull  // null이 아닌지 검사
    @field:Min(1101)  // 1101 이상
    @field:Max(3418)  // 3418 이하
    val studentId: Short? = null  // Short 타입의 studentId
}