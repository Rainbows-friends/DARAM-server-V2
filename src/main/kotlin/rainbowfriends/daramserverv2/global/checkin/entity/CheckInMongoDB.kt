package rainbowfriends.daramserverv2.global.checkin.entity

import jakarta.persistence.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Deprecated("Use CheckInDTO instead")
@Document(collection = "checkin")
data class CheckInMongoDB(
    @Id var id: String? = "",
    var userName: String,
    var studentId: Short,
    var checkinStatus: Boolean,
    var checkinDate: LocalDate
)