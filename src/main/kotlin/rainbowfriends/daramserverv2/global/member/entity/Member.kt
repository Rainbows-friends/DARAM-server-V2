package rainbowfriends.daramserverv2.global.member.entity

import jakarta.persistence.*
import rainbowfriends.daramserverv2.domain.member.exception.InvalidStudentIdException
import rainbowfriends.daramserverv2.global.member.dto.MemberDTO
import rainbowfriends.daramserverv2.global.member.enums.Roles

@Entity
@Table(name = "members_table")
@SecondaryTable(  // 코드상으론 하나의 클래스이지만 DB상에서는 두 개의 테이블로 나뉘어 저장됨
    name = "member_late_table",
    pkJoinColumns = [PrimaryKeyJoinColumn(name = "member_id", referencedColumnName = "id")]
)
data class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,
    @Column(nullable = true, unique = true)
    var email: String? = null,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val grade: Int,
    @Column(nullable = false, name = "class")
    val classNum: Int,
    @Column(nullable = false)
    val number: Int,
    @Column(nullable = false)
    val floor: Int,
    @Column(nullable = false)
    val room: Int,
    @Enumerated(EnumType.STRING)  // Enum 타입을 DB에 문자열로 저장
    @Column(nullable = false)
    val role: Roles,
    @Column(nullable = false)
    val stay: Boolean = true,
    @Column(name = "late", table = "member_late_table", nullable = false)  // member_late_table 테이블에 저장
    var lateNumber: Long? = 0
) {
    fun generateStudentId(grade: Int, classNum: Int, number: Int): Short {  // 학년, 반, 번호로 학번 생성
        if (grade < 1 || grade > 3) {
            throw InvalidStudentIdException("Invalid grade: $grade")
        }
        if (classNum < 1 || classNum > 4) {
            throw InvalidStudentIdException("Invalid classNum: $classNum")
        }
        if (number < 1 || number > 19) {
            throw InvalidStudentIdException("Invalid number: $number")
        }
        return String.format("%d%d%02d", grade, classNum, number).toShort()
    }

    fun toDto(): MemberDTO {  // Entity를 DTO로 변환하는 메서드
        return MemberDTO(
            id = id!!,
            email = this.email,
            name = this.name,
            grade = this.grade,
            classNum = this.classNum,
            number = this.number,
            floor = this.floor,
            room = this.room,
            role = this.role,
            stay = this.stay,
            lateNumber = this.lateNumber!!
        )
    }
}