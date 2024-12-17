package rainbowfriends.daramserverv2.domain.time.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import rainbowfriends.daramserverv2.domain.time.dto.enums.GetRemainTimeServiceAction
import rainbowfriends.daramserverv2.domain.time.service.RemainTimeService

@RestController
class TimeController(private val remainTimeService: RemainTimeService) {
    @GetMapping("/remaintime")
    fun getRemainTime(@RequestParam(name = "responsetype") responseType: String): Any {
        return remainTimeService.getRemainTime(GetRemainTimeServiceAction.from(responseType))
    }
}