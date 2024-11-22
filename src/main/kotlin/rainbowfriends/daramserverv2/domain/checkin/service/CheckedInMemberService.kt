package rainbowfriends.daramserverv2.domain.checkin.service

import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn

interface CheckedInMemberService {
    fun getCheckedInMember(): List<CheckIn>
}