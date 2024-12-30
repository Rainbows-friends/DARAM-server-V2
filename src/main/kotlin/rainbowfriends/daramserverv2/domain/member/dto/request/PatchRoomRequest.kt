package rainbowfriends.daramserverv2.domain.member.dto.request

import jakarta.validation.constraints.NotNull
import rainbowfriends.daramserverv2.global.annotation.ValidRoomNumber
import rainbowfriends.daramserverv2.global.annotation.ValidStudentId

data class PatchRoomRequest(
    @field:NotNull  // null이 아닌 값으로 제한
    @field:ValidStudentId  // 학번 형식 제한
    val studentId: Short,  // 학번
    @field:NotNull  // null이 아닌 값으로 제한
    @field:ValidRoomNumber  // 호실 형식 제한
    val room: Short  // 호실
)