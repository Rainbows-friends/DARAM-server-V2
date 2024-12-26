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
        if (!checkInStatusSwitch.switchCheckInStatus(  // CheckInStatusSwitch의 switchCheckInStatus 메서드를 호출
            date = LocalDate.now(),  // 현재 날짜
            studentId = request.studentId  // Request의 studentId
        )){
            throw CheckInStatusSwitchException("CheckIn Status Switch Failed")  // CheckIn Status Switch 실패 시 예외 발생
        }
    }
}