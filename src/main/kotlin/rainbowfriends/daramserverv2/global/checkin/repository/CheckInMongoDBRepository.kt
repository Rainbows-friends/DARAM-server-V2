package rainbowfriends.daramserverv2.global.checkin.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import rainbowfriends.daramserverv2.global.checkin.entity.CheckInMongoDB
import rainbowfriends.daramserverv2.global.member.entity.Member
import java.time.LocalDate

@Repository
interface CheckInMongoDBRepository : MongoRepository<CheckInMongoDB, String> {
    fun findByUser(user: Member): CheckInMongoDB?
    fun findByCheckinDate(checkinDate: LocalDate): List<CheckInMongoDB>
    fun findByCheckinStatus(checkinStatus: Boolean): List<CheckInMongoDB>
    fun findByCheckinDateAndCheckinStatus(checkinDate: LocalDate, checkinStatus: Boolean): List<CheckInMongoDB>
}