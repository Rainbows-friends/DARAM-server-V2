package rainbowfriends.daramserverv2.domain.member.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import rainbowfriends.daramserverv2.domain.member.dto.request.PatchRoomRequest
import rainbowfriends.daramserverv2.domain.member.service.CurrentMemberInquiryService
import rainbowfriends.daramserverv2.domain.member.service.MemberInquiryService
import rainbowfriends.daramserverv2.domain.member.service.PatchRoomService

@RestController
@RequestMapping("/member")
class MemberController(
    private val allMemberInquiryService: MemberInquiryService,
    private val currentMemberInquiryService: CurrentMemberInquiryService,
    private val patchRoomService: PatchRoomService
) {
    @GetMapping  // GET /member
    fun getAllMember(
        @RequestParam(required = false) id: Long?,  // ?id={Int}
        @RequestParam(required = false) stay: Boolean?,  // ?stay={Boolean}
        @RequestParam(required = false) floor: Int?,  // ?floor={Int}
        @RequestParam(required = false) room: Int?,  // ?room={Int}
        @RequestParam(required = false) grade: Int?,  // ?grade={Int}
        @RequestParam(required = false) classNum: Int?  // ?classNum={Int}
    ) = allMemberInquiryService.getAllMember(id, stay, floor, room, grade, classNum)

    @GetMapping("/current")  // GET /member/current
    fun getCurrentMember(request: HttpServletRequest) = currentMemberInquiryService.getCurrentMember(request)  // HttpServletRequest를 통해 현재 Member 조회

    @PatchMapping("/room")  // PATCH /member/room
    @ResponseStatus(HttpStatus.NO_CONTENT)  // 204 No Content
    fun patchRoom(@RequestBody patchRoomRequest: PatchRoomRequest) {  // Request Body로 PatchRoomRequest를 받아서
        patchRoomService.patchRoom(patchRoomRequest)  // patchRoomService를 통해 Room 정보 수정
    }
}