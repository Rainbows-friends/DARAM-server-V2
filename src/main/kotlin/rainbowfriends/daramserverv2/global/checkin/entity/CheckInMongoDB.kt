package rainbowfriends.daramserverv2.global.checkin.entity

import jakarta.persistence.Id
import org.springframework.data.mongodb.core.mapping.Document
import rainbowfriends.daramserverv2.global.checkin.dto.CheckInDTO
import java.time.LocalDate

@Document(collection = "checkin")
data class CheckInMongoDB(
    @Id var id: String? = "",
    var userName: String,
    var studentId: Short,
    var checkinStatus: Boolean,
    var checkinDate: LocalDate
){
    fun toDto() = CheckInDTO(
        id = id!!.toLong(),
        userName = userName,
        studentId = studentId,
        checkinStatus = checkinStatus,
        checkinDate = checkinDate
    )
}