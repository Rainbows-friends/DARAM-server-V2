package rainbowfriends.daramserverv2.global.checkin.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import rainbowfriends.daramserverv2.global.checkin.dto.CheckInDTO
import rainbowfriends.daramserverv2.global.member.entity.Member
import java.time.LocalDate

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
    @Column(name = "checkin_date", nullable = false)
    val checkinDate: LocalDate = LocalDate.now()
) {
    fun toDTO(): CheckInDTO {
        return CheckInDTO(
            id = this.id,
            userName = this.user.name,
            studentId = this.user.generateStudentId(
                grade = this.user.grade,
                classNum = this.user.classNum,
                number = this.user.number
            ),
            checkinStatus = this.checkinStatus,
            checkinDate = this.checkinDate
        )
    }
}