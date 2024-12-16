package rainbowfriends.daramserverv2.domain.checkin.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.checkin.dto.enums.GetCheckInComponentAction
import rainbowfriends.daramserverv2.global.checkin.entity.CheckInMongoDB
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInMongoDBRepository
import java.time.LocalDate

@Component
class CheckInMemberQuery(private val checkInMongoDBRepository: CheckInMongoDBRepository) {
    fun getCheckInMember(action: GetCheckInComponentAction, date: LocalDate): List<CheckInMongoDB> =
        when (action) {
            GetCheckInComponentAction.GET_CHECKED_IN_MEMBER -> getCheckInMember(date)
            GetCheckInComponentAction.GET_MISSED_CHECK_IN_MEMBER -> getMissedCheckInMember(date)
        }

    private fun getMissedCheckInMember(date: LocalDate): List<CheckInMongoDB> {
        return checkInMongoDBRepository.findByCheckinDateAndCheckinStatus(date, false)
    }

    private fun getCheckInMember(date: LocalDate): List<CheckInMongoDB> {
        return checkInMongoDBRepository.findByCheckinDateAndCheckinStatus(date, true)
    }
}