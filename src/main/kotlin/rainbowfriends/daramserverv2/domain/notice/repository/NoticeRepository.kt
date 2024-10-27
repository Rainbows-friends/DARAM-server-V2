package rainbowfriends.daramserverv2.domain.notice.repository

import org.springframework.data.jpa.repository.JpaRepository
import rainbowfriends.daramserverv2.domain.notice.entity.Notice

interface NoticeRepository : JpaRepository<Notice, Long> {
    fun findByTitle(title: String): Notice?
}