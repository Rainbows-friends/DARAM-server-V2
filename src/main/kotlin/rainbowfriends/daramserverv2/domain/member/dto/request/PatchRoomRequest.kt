package rainbowfriends.daramserverv2.domain.member.dto.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class PatchRoomRequest(
    @field:NotNull  // null이 아닌 값으로 제한
    @field:Max(3418)  // 값의 최대값을 3418로 제한
    @field:Min(1101)  // 값의 최소값을 1101로 제한
    val studentId: Short,  // 학번
    @field:NotNull  // null이 아닌 값으로 제한
    @field:Max(518)  // 값의 최대값을 520로 제한
    @field:Min(201)  // 값의 최소값을 201로 제한
    val room: Short  // 호실
)
