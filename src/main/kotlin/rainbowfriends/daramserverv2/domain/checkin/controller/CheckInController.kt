package rainbowfriends.daramserverv2.domain.checkin.controller

import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import rainbowfriends.daramserverv2.domain.checkin.dto.request.CheckInStatusSwitchRequest
import rainbowfriends.daramserverv2.domain.checkin.dto.response.GetCheckInResponse
import rainbowfriends.daramserverv2.domain.checkin.service.CheckInStatusSwitchService
import rainbowfriends.daramserverv2.domain.checkin.service.CheckedInMemberService
import rainbowfriends.daramserverv2.domain.checkin.service.MissedCheckInMemberService

@RestController
@RequestMapping("/checkin")
class CheckInController(
    private val checkedInMemberService: CheckedInMemberService,
    private val missedCheckInMemberService: MissedCheckInMemberService,
    private val checkInStatusSwitchService: CheckInStatusSwitchService
) {
    @GetMapping("/checkin")  // GET /checkin/checkin
    fun getCheckInMember(): List<GetCheckInResponse> {
        return checkedInMemberService.getCheckedInMember()
    }

    @GetMapping("/uncheckin")  // GET /checkin/uncheckin
    fun getUnCheckInMember(): List<GetCheckInResponse> {
        return missedCheckInMemberService.getMissedCheckInMember()
    }

    @PatchMapping  // PATCH /checkin
    @ResponseStatus(HttpStatus.NO_CONTENT)  // 204 No Content
    fun switchCheckInStatus(@RequestBody @Validated request: CheckInStatusSwitchRequest) {  // Request의 Body로 CheckInStatusSwitchRequest를 받음
        checkInStatusSwitchService.switchCheckInStatus(request)
    }
}