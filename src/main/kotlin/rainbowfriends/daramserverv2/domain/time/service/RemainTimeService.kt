package rainbowfriends.daramserverv2.domain.time.service

import org.springframework.http.ResponseEntity
import rainbowfriends.daramserverv2.domain.time.dto.enums.ResponseType

interface RemainTimeService {
    fun getRemainTime(responseType: ResponseType): Any
}