package rainbowfriends.daramserverv2.domain.time.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import rainbowfriends.daramserverv2.domain.time.dto.enums.GetRemainTimeServiceAction
import rainbowfriends.daramserverv2.domain.time.service.RemainTimeService

@Tag(name = "Time", description = "시간 API")
@RestController
@RequestMapping("/time")
class TimeController(private val remainTimeService: RemainTimeService) {
    @Operation(summary = "입사 마감 남은 시간 조회", description = "입사 마감까지 남은 시간을 조회합니다")
    @GetMapping("/remaintime")
    fun getRemainTime(@RequestParam(name = "responsetype") responseType: String): Any {
        return remainTimeService.getRemainTime(GetRemainTimeServiceAction.from(responseType) )
    }
}