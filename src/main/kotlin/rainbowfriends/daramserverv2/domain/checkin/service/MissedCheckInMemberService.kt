package rainbowfriends.daramserverv2.domain.checkin.service

import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn

interface MissedCheckInMemberService {
    fun getMissedCheckInMember(): List<CheckIn>
}