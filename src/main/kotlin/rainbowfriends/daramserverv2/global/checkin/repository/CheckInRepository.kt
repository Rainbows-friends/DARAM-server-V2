package rainbowfriends.daramserverv2.global.checkin.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn
import rainbowfriends.daramserverv2.global.member.entity.Member
import java.time.LocalDate

@Repository
interface CheckInRepository : JpaRepository<CheckIn, Long> {
    @EntityGraph(attributePaths = ["user"])  // user 필드를 Eager로 로딩(지연 로딩 방지)
    override fun findAll(pageable: Pageable): Page<CheckIn> // 페이징 처리를 위한 findAll 메서드
    fun findByUserAndCheckinInfoDate(user: Member, checkinDate: LocalDate): CheckIn
    fun findByCheckinInfoDateAndCheckinStatus(checkinDate: LocalDate, checkinStatus: Boolean): List<CheckIn>
    fun findByCheckinInfoDate(checkinDate: LocalDate): List<CheckIn>
}