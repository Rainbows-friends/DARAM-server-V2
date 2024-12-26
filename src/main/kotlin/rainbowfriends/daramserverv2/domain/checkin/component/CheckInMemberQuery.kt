package rainbowfriends.daramserverv2.domain.checkin.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.checkin.dto.enums.GetCheckInComponentAction
import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import java.time.LocalDate
/*
Made by 재욱
 */
@Component
class CheckInMemberQuery(private val checkInRepository: CheckInRepository) {
    fun getCheckInMember(action: GetCheckInComponentAction, date: LocalDate): List<CheckIn> =
        when (action) {  // Kotlin의 Switch-Case문 기능을 하는 when문을 사용하여 action에 따라 다른 동작을 수행
            GetCheckInComponentAction.GET_CHECKED_IN_MEMBER -> getCheckInMember(date)
            GetCheckInComponentAction.GET_MISSED_CHECK_IN_MEMBER -> getMissedCheckInMember(date)
        }

    private fun getMissedCheckInMember(date: LocalDate): List<CheckIn> {
        return checkInRepository.findByCheckinInfoDateAndCheckinStatus(date, false)  // 해당 날짜에 체크인을 하지 않은 회원들을 반환
    }

    private fun getCheckInMember(date: LocalDate): List<CheckIn> {
        return checkInRepository.findByCheckinInfoDateAndCheckinStatus(date, true)  // 해당 날짜에 체크인을 한 회원들을 반환
    }
}