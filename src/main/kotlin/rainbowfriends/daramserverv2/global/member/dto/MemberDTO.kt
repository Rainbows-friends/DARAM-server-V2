package rainbowfriends.daramserverv2.global.member.dto

import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.enums.Roles

data class MemberDTO(
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
    fun toEntity(): Member {
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