package rainbowfriends.daramserverv2.domain.member.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.member.component.MemberFind
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.domain.member.service.MemberInqueryService
import rainbowfriends.daramserverv2.global.member.entity.Member

@Service
class MemberInqueryServiceImpl(
    private val memberFind: MemberFind
) : MemberInqueryService {
    override fun getAllMember(
        id: Long?,
        stay: Boolean?,
        floor: Int?,
        room: Int?,
        grade: Int?,
        classNum: Int?
    ): List<Member> {
        val allMembers = memberFind.findMemberByCache()
        val filteredMembers = allMembers
            .let { filterMembersById(it, id) }
            .let { filterMembersByStay(it, stay) }
            .let { filterMembersByFloor(it, floor) }
            .let { filterMembersByRoom(it, room) }
            .let { filterMembersByGrade(it, grade) }
            .let { filterMembersByClassNum(it, classNum) }
        if (filteredMembers.isEmpty()) {
            throw MemberNotFoundException("No member found")
        }
        return filteredMembers
    }

    private fun filterMembersById(members: List<Member>, id: Long?): List<Member> {
        return if (id == null) members else members.filter { it.id == id }
    }

    private fun filterMembersByStay(members: List<Member>, stay: Boolean?): List<Member> {
        return if (stay == null) members else members.filter { it.stay == stay }
    }

    private fun filterMembersByFloor(members: List<Member>, floor: Int?): List<Member> {
        return if (floor == null) members else members.filter { it.floor == floor }
    }

    private fun filterMembersByRoom(members: List<Member>, room: Int?): List<Member> {
        return if (room == null) members else members.filter { it.room == room }
    }

    private fun filterMembersByGrade(members: List<Member>, grade: Int?): List<Member> {
        return if (grade == null) members else members.filter { it.grade == grade }
    }

    private fun filterMembersByClassNum(members: List<Member>, classNum: Int?): List<Member> {
        return if (classNum == null) members else members.filter { it.classNum == classNum }
    }
}