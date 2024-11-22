package rainbowfriends.daramserverv2.domain.checkin.service

import rainbowfriends.daramserverv2.domain.checkin.dto.request.CheckInStatusSwitchRequest

interface CheckInStatusSwitchService {
    fun switchCheckInStatus(request: CheckInStatusSwitchRequest)
}