package rainbowfriends.daramserverv2.domain.notice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import rainbowfriends.daramserverv2.domain.notice.dto.request.PatchNoticeRequest
import rainbowfriends.daramserverv2.domain.notice.dto.request.PostNoticeRequest
import rainbowfriends.daramserverv2.domain.notice.dto.response.NoticeResponse
import rainbowfriends.daramserverv2.domain.notice.service.*

@Tag(name = "Notice", description = "공지 등록/수정 API")
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
    @Operation(summary = "공지 조회", description = "특정 공지를 조회합니다")
    fun getNotice(@PathVariable id: Long): NoticeResponse {
        return getNoticeService.getNotice(id)
    }

    @GetMapping("/all")
    @Operation(summary = "모든 공지 조회", description = "모든 공지를 조회합니다")
    fun getAllNotices(): List<NoticeResponse> {
        return getAllNoticeService.getAllNotices()
    }

    @PostMapping
    @Operation(summary = "공지 등록", description = "공지를 등록합니다")
    fun postNotice(request: HttpServletRequest, @RequestBody requestDto: PostNoticeRequest): NoticeResponse {
        return postNoticeService.postNotice(request, requestDto)
    }

    @PatchMapping("/{id}")
    @Operation(summary = "공지 수정", description = "특정 공지를 수정합니다")
    fun patchNotice(@PathVariable id: Long, @RequestBody request: PatchNoticeRequest): NoticeResponse {
        return patchNoticeService.patchNotice(id, request)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "공지 삭제", description = "특정 공지를 삭제합니다")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteNotice(@PathVariable id: Long) {
        deleteNoticeService.deleteNotice(id)
    }
}