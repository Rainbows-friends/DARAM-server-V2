package rainbowfriends.daramserverv2.domain.time.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import rainbowfriends.daramserverv2.domain.time.dto.enums.GetRemainTimeServiceAction
import rainbowfriends.daramserverv2.domain.time.service.RemainTimeService

@RestController
@RequestMapping("/time")
class TimeController(private val remainTimeService: RemainTimeService) {
    @GetMapping("/remaintime")  // GET /time/remaintime?responsetype={responsetype}
    fun getRemainTime(@RequestParam(name = "responsetype") responseType: String): Any {  // responsetype은 RequestParam으로 받아옴
        return remainTimeService.getRemainTime(GetRemainTimeServiceAction.from(responseType))  // 받아온 responsetype을 Enum으로 변환하여 서비스에 전달
    }
}