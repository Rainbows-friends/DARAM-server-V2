package rainbowfriends.daramserverv2.domain.checkin.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.checkin.component.CheckInMemberQuery
import rainbowfriends.daramserverv2.domain.checkin.dto.GetCheckInComponentAction
import rainbowfriends.daramserverv2.domain.checkin.service.CheckedInMemberService
import rainbowfriends.daramserverv2.global.checkin.entity.CheckInMongoDB
import java.time.LocalDate

@Service
class CheckedInMemberServiceImpl(private val checkInMemberQuery: CheckInMemberQuery) : CheckedInMemberService {
    override fun getCheckedInMember(): List<CheckInMongoDB> {
        return checkInMemberQuery.getCheckInMember(
            GetCheckInComponentAction.GET_CHECKED_IN_MEMBER,
            LocalDate.now()
        )
    }
}