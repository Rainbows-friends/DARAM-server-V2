package rainbowfriends.daramserverv2.domain.notice.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import rainbowfriends.daramserverv2.domain.notice.dto.request.PatchNoticeRequest
import rainbowfriends.daramserverv2.domain.notice.dto.request.PostNoticeRequest
import rainbowfriends.daramserverv2.domain.notice.dto.response.NoticeResponse
import rainbowfriends.daramserverv2.domain.notice.service.*

@RequestMapping("/notice")
@RestController
class NoticeController(
    private val getNoticeService: GetNoticeService,
    private val getAllNoticeService: GetAllNoticeService,
    private val postNoticeService: PostNoticeService,
    private val patchNoticeService: PatchNoticeService,
    private val deleteNoticeService: DeleteNoticeService
) {

    @GetMapping("/{id}") // GET /notice/{id}
    fun getNotice(@PathVariable id: Long): NoticeResponse {
        return getNoticeService.getNotice(id)
    }

    @GetMapping("/all")  // GET /notice/all
    fun getAllNotices(): List<NoticeResponse> {
        return getAllNoticeService.getAllNotices()
    }

    @PostMapping   // POST /notice
    fun postNotice(request: HttpServletRequest, @RequestBody requestDto: PostNoticeRequest): NoticeResponse {  // HttpServletRequest 객체와 PostNoticeRequest 객체를 인자로 받아 NoticeResponse 객체 반환
        return postNoticeService.postNotice(request, requestDto)
    }

    @PatchMapping("/{id}")  // PATCH /notice/{id}
    fun patchNotice(@PathVariable id: Long, @RequestBody request: PatchNoticeRequest): NoticeResponse {  // PathVariable로 id를 받고, RequestBody로 PatchNoticeRequest 객체를 받아 NoticeResponse 객체 반환
        return patchNoticeService.patchNotice(id, request)
    }

    @DeleteMapping("/{id}")  // DELETE /notice/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)  // 204 No Content
    fun deleteNotice(@PathVariable id: Long) {  // PathVariable로 id를 받아 공지사항 삭제
        deleteNoticeService.deleteNotice(id)
    }
}