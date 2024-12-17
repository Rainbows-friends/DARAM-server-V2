package rainbowfriends.daramserverv2.global.checkin.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import rainbowfriends.daramserverv2.global.checkin.dto.CheckInDTO
import rainbowfriends.daramserverv2.global.member.entity.Member
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "checkin_table")
data class CheckIn(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: Member,
    @Column(name = "checkin_status", nullable = false)
    var checkinStatus: Boolean = false,
    @Column(name = "checkin_info_make_date", nullable = false)
    val checkinInfoDate: LocalDate = LocalDate.now(),
    @Column(name = "checkin_date")
    var checkinDate: LocalDateTime? = null
) {
    fun toDTO(): CheckInDTO {
        return CheckInDTO(
            id = this.id,
            user = this.user,
            checkinStatus = this.checkinStatus,
            checkinDate = this.checkinDate
        )
    }
}