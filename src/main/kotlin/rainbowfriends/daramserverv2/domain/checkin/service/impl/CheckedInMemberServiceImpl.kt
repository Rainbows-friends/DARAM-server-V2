package rainbowfriends.daramserverv2.domain.checkin.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.checkin.component.CheckInMemberQuery
import rainbowfriends.daramserverv2.domain.checkin.dto.enums.GetCheckInComponentAction
import rainbowfriends.daramserverv2.domain.checkin.dto.response.GetCheckInResponse
import rainbowfriends.daramserverv2.domain.checkin.service.CheckedInMemberService
import rainbowfriends.daramserverv2.global.checkin.dto.CheckInDTO
import java.time.LocalDate

@Service
class CheckedInMemberServiceImpl(private val checkInMemberQuery: CheckInMemberQuery) : CheckedInMemberService {
    override fun getCheckedInMember(): List<GetCheckInResponse> {
        val checkInData: List<CheckInDTO> = checkInMemberQuery.getCheckInMember(  // CheckInMemberQuery의 getCheckInMember 메서드를 호출
            GetCheckInComponentAction.GET_CHECKED_IN_MEMBER,  // GetCheckInComponentAction.GET_CHECKED_IN_MEMBER
            LocalDate.now()  // 현재 날짜
        ).map {  // CheckInDTO를 GetCheckInResponse로 변환
            CheckInDTO(
                id = it.id,
                user = it.user,
                checkinStatus = it.checkinStatus,
                checkinDate = it.checkinDate
            )
        }
        return checkInData.map { checkIn ->  // CheckInDTO를 GetCheckInResponse로 변환
            GetCheckInResponse(
                id = checkIn.id,
                user = checkIn.user.toDto(),
                checkinStatus = checkIn.checkinStatus,
                checkinDate = String.format(
                    "%02d:%02d:%02d",  // 시:분:초 형식으로 변환
                    checkIn.checkinDate?.hour,  // 시
                    checkIn.checkinDate?.minute,  // 분
                    checkIn.checkinDate?.second  // 초
                )
            )
        }
    }
}