package rainbowfriends.daramserverv2.domain.checkin.service

import rainbowfriends.daramserverv2.global.checkin.entity.CheckInMongoDB

interface MissedCheckInMemberService {
    fun getMissedCheckInMember(): List<CheckInMongoDB>
}