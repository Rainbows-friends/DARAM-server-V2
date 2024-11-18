package rainbowfriends.daramserverv2.domain.member.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.member.component.MemberFindbyElasticsearch
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.domain.member.service.AllMemberInqueryService
import rainbowfriends.daramserverv2.global.member.entity.MemberElasticsearch

@Service
class AllMemberInqueryServiceImpl(
    private val memberFindbyElasticsearch: MemberFindbyElasticsearch
) : AllMemberInqueryService {
    override fun getAllMember(
        stay: Boolean?,
        floor: Int?,
        room: Int?,
        grade: Int?,
        classNum: Int?
    ): List<MemberElasticsearch> {
        val allMembers = memberFindbyElasticsearch.findMemberByElasticsearch()
        val filteredMembers = allMembers
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

    private fun filterMembersByStay(members: List<MemberElasticsearch>, stay: Boolean?): List<MemberElasticsearch> {
        return if (stay == null) members else members.filter { it.stay == stay }
    }

    private fun filterMembersByFloor(members: List<MemberElasticsearch>, floor: Int?): List<MemberElasticsearch> {
        return if (floor == null) members else members.filter { it.floor == floor }
    }

    private fun filterMembersByRoom(members: List<MemberElasticsearch>, room: Int?): List<MemberElasticsearch> {
        return if (room == null) members else members.filter { it.room == room }
    }

    private fun filterMembersByGrade(members: List<MemberElasticsearch>, grade: Int?): List<MemberElasticsearch> {
        return if (grade == null) members else members.filter { it.grade == grade }
    }

    private fun filterMembersByClassNum(members: List<MemberElasticsearch>, classNum: Int?): List<MemberElasticsearch> {
        return if (classNum == null) members else members.filter { it.classNum == classNum }
    }
}