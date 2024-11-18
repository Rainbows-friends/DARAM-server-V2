package rainbowfriends.daramserverv2.domain.member.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import rainbowfriends.daramserverv2.domain.member.service.MemberInqueryService

@RestController
@RequestMapping("/member")
@Tag(name = "Member", description = "회원 조회 API")
class MemberController(
    private val allMemberInqueryService: MemberInqueryService
) {
    @GetMapping
    @Operation(summary = "모든 회원 조회", description = "모든 회원을 조회합니다.")
    fun getAllMember(
        @RequestParam(required = false) id: Long?,
        @RequestParam(required = false) stay: Boolean?,
        @RequestParam(required = false) floor: Int?,
        @RequestParam(required = false) room: Int?,
        @RequestParam(required = false) grade: Int?,
        @RequestParam(required = false) classNum: Int?
    ) = allMemberInqueryService.getAllMember(id, stay, floor, room, grade, classNum)
}