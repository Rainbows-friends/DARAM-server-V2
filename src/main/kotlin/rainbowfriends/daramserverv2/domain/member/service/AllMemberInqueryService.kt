package rainbowfriends.daramserverv2.domain.member.service

import rainbowfriends.daramserverv2.global.member.entity.MemberElasticsearch

interface AllMemberInqueryService {
    fun getAllMember(stay: Boolean?, floor: Int?, room: Int?, grade: Int?, classNum: Int?): List<MemberElasticsearch>
}