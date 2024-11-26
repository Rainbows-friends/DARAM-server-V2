package rainbowfriends.daramserverv2.domain.checkin.service

import rainbowfriends.daramserverv2.domain.checkin.dto.response.GetCheckInResponse

interface CheckedInMemberService {
    fun getCheckedInMember(): List<GetCheckInResponse>
}