package rainbowfriends.daramserverv2.global.checkin.entity

import jakarta.persistence.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import rainbowfriends.daramserverv2.global.member.entity.Member
import java.time.LocalDate

@Document(collection = "checkin")
data class CheckInMongoDB(
    @Id val id: String = "",
    @DBRef val user: Member,
    var checkinStatus: Boolean,
    val checkinDate: LocalDate
)