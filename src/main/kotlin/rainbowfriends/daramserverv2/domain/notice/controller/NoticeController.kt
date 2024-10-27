package rainbowfriends.daramserverv2.domain.notice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import rainbowfriends.daramserverv2.domain.notice.dto.request.CreateOrUpdateNoticeRequest
import rainbowfriends.daramserverv2.domain.notice.entity.Notice
import rainbowfriends.daramserverv2.domain.notice.service.PostNoticeService

@Tag(name = "Notice", description = "공지 등록/수정 API")
@RequestMapping("/notice")
@RestController
class NoticeController(
    private val postNoticeService: PostNoticeService
) {
    @PostMapping
    @Operation(summary = "공지 등록", description = "공지를 등록합니다")
    fun postNotice(request: HttpServletRequest, @RequestBody requestDto: CreateOrUpdateNoticeRequest): Notice {
        return postNoticeService.postNotice(request, requestDto)
    }
}