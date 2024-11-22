package rainbowfriends.daramserverv2.domain.checkin.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.checkin.component.CheckInStatusSwitch
import rainbowfriends.daramserverv2.domain.checkin.dto.request.CheckInStatusSwitchRequest
import rainbowfriends.daramserverv2.domain.checkin.service.CheckInStatusSwitchService
import rainbowfriends.daramserverv2.global.checkin.exception.CheckInStatusSwitchException
import java.time.LocalDate

@Service
class CheckInStatusSwitchServiceImpl(private val checkInStatusSwitch: CheckInStatusSwitch): CheckInStatusSwitchService {
    override fun switchCheckInStatus(request: CheckInStatusSwitchRequest) {
        if (!checkInStatusSwitch.switchCheckInStatus(
            date = LocalDate.now(),
            studentId = request.StudentId
        )){
            throw CheckInStatusSwitchException("CheckIn Status Switch Failed")
        }
    }
}