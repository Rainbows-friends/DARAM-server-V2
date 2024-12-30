package rainbowfriends.daramserverv2.domain.checkin.dto.request

import jakarta.validation.constraints.NotNull
import rainbowfriends.daramserverv2.global.annotation.ValidStudentId

class CheckInStatusSwitchRequest {
    @field:NotNull  // null이 아닌지 검사
    @field:ValidStudentId  // 학번 유효성 검사
    val studentId: Short? = null  // Short 타입의 studentId
}