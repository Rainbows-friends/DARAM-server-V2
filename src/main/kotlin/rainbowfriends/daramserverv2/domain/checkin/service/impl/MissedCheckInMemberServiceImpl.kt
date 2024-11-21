package rainbowfriends.daramserverv2.domain.checkin.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.checkin.component.CheckInMemberQuery
import rainbowfriends.daramserverv2.domain.checkin.dto.GetCheckInComponentAction
import rainbowfriends.daramserverv2.domain.checkin.service.MissedCheckInMemberService
import rainbowfriends.daramserverv2.global.checkin.entity.CheckInMongoDB
import java.time.LocalDate

@Service
class MissedCheckInMemberServiceImpl(private val checkInMemberQuery: CheckInMemberQuery) : MissedCheckInMemberService {
    override fun getMissedCheckInMember(): List<CheckInMongoDB> {
        return checkInMemberQuery.getCheckInMember(
            GetCheckInComponentAction.GET_MISSED_CHECK_IN_MEMBER,
            LocalDate.now()
        )
    }
}