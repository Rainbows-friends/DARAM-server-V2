package rainbowfriends.daramserverv2.global.checkin.repository

import io.lettuce.core.dynamic.annotation.Param
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

import org.springframework.stereotype.Repository
import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn
import rainbowfriends.daramserverv2.global.member.entity.Member
import java.time.LocalDate

@Repository
interface CheckInRepository : JpaRepository<CheckIn, Long> {
    @EntityGraph(attributePaths = ["user"])
    override fun findAll(pageable: Pageable): Page<CheckIn>
    fun findByUserId(userId: Long): List<CheckIn>
    fun findByUserIdAndCheckinDate(user_id: Long, checkinDate: LocalDate): CheckIn?
    fun findByCheckinDateBefore(checkinDate: LocalDate): List<CheckIn>
    @Modifying
    @Query("DELETE FROM CheckIn WHERE checkinDate < :cutoffDate")
    fun deleteCheckInsBeforeDate(@Param("cutoffDate") cutoffDate: LocalDate): Int
    fun findByUserAndCheckinDate(user: Member, checkinDate: LocalDate): CheckIn
    fun findByCheckinDate(checkinDate: LocalDate): List<CheckIn>
    fun findByCheckinDateBetween(checkinDate: LocalDate, checkinDate2: LocalDate): List<CheckIn>
    fun findByCheckinDateBetweenAndUserId(checkinDate: LocalDate, checkinDate2: LocalDate, user_id: Long): List<CheckIn>
    fun deleteByCheckinDate(checkinDate: LocalDate): Boolean
}