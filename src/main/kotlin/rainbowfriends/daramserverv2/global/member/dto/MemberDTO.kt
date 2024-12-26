package rainbowfriends.daramserverv2.global.member.dto

import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.enums.Roles

data class MemberDTO(  // Member Entity의 복제본인 DTO
    val id: Long? = 0,
    var email: String? = null,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val number: Int,
    val floor: Int,
    val room: Int,
    val role: Roles,
    val stay: Boolean = true,
    val lateNumber: Long = 0
) {
    fun toEntity(): Member {  // DTO를 Entity로 변환하는 메서드
        return Member(
            id = id,
            email = email,
            name = name,
            grade = grade,
            classNum = classNum,
            number = number,
            floor = floor,
            room = room,
            role = role,
            stay = stay,
            lateNumber = lateNumber
        )
    }
}