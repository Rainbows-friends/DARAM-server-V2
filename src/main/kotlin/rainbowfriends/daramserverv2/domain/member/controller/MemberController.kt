package rainbowfriends.daramserverv2.domain.member.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import rainbowfriends.daramserverv2.domain.member.service.CurrentMemberInqueryService
import rainbowfriends.daramserverv2.domain.member.service.MemberInqueryService

@RestController
@RequestMapping("/member")
class MemberController(
    private val allMemberInqueryService: MemberInqueryService,
    private val currentMemberInqueryService: CurrentMemberInqueryService
) {
    @GetMapping  // GET /member
    fun getAllMember(
        @RequestParam(required = false) id: Long?,  // ?id={Int}
        @RequestParam(required = false) stay: Boolean?,  // ?stay={Boolean}
        @RequestParam(required = false) floor: Int?,  // ?floor={Int}
        @RequestParam(required = false) room: Int?,  // ?room={Int}
        @RequestParam(required = false) grade: Int?,  // ?grade={Int}
        @RequestParam(required = false) classNum: Int?  // ?classNum={Int}
    ) = allMemberInqueryService.getAllMember(id, stay, floor, room, grade, classNum)

    @GetMapping("/current")  // GET /member/current
    fun getCurrentMember(request: HttpServletRequest) = currentMemberInqueryService.getCurrentMember(request)  // HttpServletRequest를 통해 현재 Member 조회
}