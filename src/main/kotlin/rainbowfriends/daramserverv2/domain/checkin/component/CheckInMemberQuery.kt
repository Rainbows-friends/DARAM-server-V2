package rainbowfriends.daramserverv2.domain.checkin.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.checkin.dto.GetCheckInComponentAction
import rainbowfriends.daramserverv2.global.checkin.entity.CheckInMongoDB
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInMongoDBRepository
import java.time.LocalDate

@Component
class CheckInMemberQuery(
    private val checkInMongoDBRepository: CheckInMongoDBRepository
) {
    fun getCheckInMember(action: GetCheckInComponentAction, date: LocalDate): List<CheckInMongoDB> {
        if (action == GetCheckInComponentAction.GET_CHECKED_IN_MEMBER) {
            return checkInMongoDBRepository.findByCheckinDateAndCheckinStatus(date, true)
        } else if (action == GetCheckInComponentAction.GET_MISSED_CHECK_IN_MEMBER) {
            return checkInMongoDBRepository.findByCheckinDateAndCheckinStatus(date, false)
        }
        return checkInMongoDBRepository.findAll()
    }
}