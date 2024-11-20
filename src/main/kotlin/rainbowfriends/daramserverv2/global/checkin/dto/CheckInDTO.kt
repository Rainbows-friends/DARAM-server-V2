package rainbowfriends.daramserverv2.global.checkin.dto

import rainbowfriends.daramserverv2.global.checkin.entity.CheckInMongoDB
import java.time.LocalDate

data class CheckInDTO(
    val id: Long?,
    val userName: String,
    val studentId: Short,
    val checkinStatus: Boolean,
    val checkinDate: LocalDate
) {
    fun toMongoDBDocument(): CheckInMongoDB {
        return CheckInMongoDB(
            id = this.id.toString(),
            userName = this.userName,
            studentId = this.studentId,
            checkinStatus = this.checkinStatus,
            checkinDate = this.checkinDate
        )
    }
}