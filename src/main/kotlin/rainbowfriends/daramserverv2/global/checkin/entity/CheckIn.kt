package rainbowfriends.daramserverv2.global.checkin.entity

import jakarta.persistence.*
import rainbowfriends.daramserverv2.global.member.entity.Member
import java.time.LocalDate

@Entity
@Table(name = "checkin")
data class CheckIn(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val user: Member,
    @Column(name = "checkin_status", nullable = false)
    var checkinStatus: Boolean = false,
    @Column(name = "checkin_date", nullable = false)
    val checkinDate: LocalDate = LocalDate.now()
) {
    fun toMongoDBDocument(): CheckInMongoDB {
        return CheckInMongoDB(
            id = this.id.toString(),
            user = this.user,
            checkinStatus = this.checkinStatus,
            checkinDate = this.checkinDate
        )
    }
}