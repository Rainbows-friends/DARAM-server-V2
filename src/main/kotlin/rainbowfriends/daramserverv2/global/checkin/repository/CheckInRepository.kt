package rainbowfriends.daramserverv2.global.checkin.repository

import org.springframework.data.jpa.repository.JpaRepository
import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn
import java.time.LocalDate

interface CheckInRepository : JpaRepository<CheckIn, Long> {
    fun findByUserId(userId: Long): List<CheckIn>
    fun findByUserIdAndCheckinDate(user_id: Long, checkinDate: LocalDate): CheckIn?
    fun findByCheckinDate(checkinDate: LocalDate): List<CheckIn>
    fun findByCheckinDateBetween(checkinDate: LocalDate, checkinDate2: LocalDate): List<CheckIn>
    fun findByCheckinDateBetweenAndUserId(checkinDate: LocalDate, checkinDate2: LocalDate, user_id: Long): List<CheckIn>
}