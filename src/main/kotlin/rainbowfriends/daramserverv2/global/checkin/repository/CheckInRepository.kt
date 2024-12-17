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
    @EntityGraph(attributePaths = ["user"])
    override fun findAll(pageable: Pageable): Page<CheckIn>
    fun findByUserAndCheckinInfoDate(user: Member, checkinDate: LocalDate): CheckIn
    fun findByCheckinInfoDateAndCheckinStatus(checkinDate: LocalDate, checkinStatus: Boolean): List<CheckIn>
    fun findByCheckinInfoDate(checkinDate: LocalDate): List<CheckIn>
}