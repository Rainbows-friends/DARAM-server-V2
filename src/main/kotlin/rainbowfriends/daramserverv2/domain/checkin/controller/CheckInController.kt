package rainbowfriends.daramserverv2.domain.checkin.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import rainbowfriends.daramserverv2.domain.checkin.dto.request.CheckInStatusSwitchRequest
import rainbowfriends.daramserverv2.domain.checkin.service.CheckInStatusSwitchService
import rainbowfriends.daramserverv2.domain.checkin.service.CheckedInMemberService
import rainbowfriends.daramserverv2.domain.checkin.service.MissedCheckInMemberService
import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn

@RestController
@RequestMapping("/checkin")
@Tag(name = "CheckIn", description = "기숙사 입사 API")
class CheckInController(
    private val checkedInMemberService: CheckedInMemberService,
    private val missedCheckInMemberService: MissedCheckInMemberService,
    private val checkInStatusSwitchService: CheckInStatusSwitchService
) {
    @Operation(summary = "입실 상태 학생 조회", description = "입실 상태인 학생을 조회합니다")
    @GetMapping("/checkin")
    fun getCheckInMember(): List<CheckIn> {
        return checkedInMemberService.getCheckedInMember()
    }

    @Operation(summary = "미입실 상태 학생 조회", description = "미입실 상태인 학생을 조회합니다")
    @GetMapping("/uncheckin")
    fun getUnCheckInMember(): List<CheckIn> {
        return missedCheckInMemberService.getMissedCheckInMember()
    }

    @Operation(summary = "입실 상태 전환", description = "입실 상태를 전환합니다")
    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun switchCheckInStatus(@RequestBody request: CheckInStatusSwitchRequest) {
        checkInStatusSwitchService.switchCheckInStatus(request)
    }
}