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

    @GetMapping("/{id}")
    fun getNotice(@PathVariable id: Long): NoticeResponse {
        return getNoticeService.getNotice(id)
    }

    @GetMapping("/all")
    fun getAllNotices(): List<NoticeResponse> {
        return getAllNoticeService.getAllNotices()
    }

    @PostMapping
    fun postNotice(request: HttpServletRequest, @RequestBody requestDto: PostNoticeRequest): NoticeResponse {
        return postNoticeService.postNotice(request, requestDto)
    }

    @PatchMapping("/{id}")
    fun patchNotice(@PathVariable id: Long, @RequestBody request: PatchNoticeRequest): NoticeResponse {
        return patchNoticeService.patchNotice(id, request)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteNotice(@PathVariable id: Long) {
        deleteNoticeService.deleteNotice(id)
    }
}