package rainbowfriends.daramserverv2.domain.member.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.member.component.FindAllMember
import rainbowfriends.daramserverv2.domain.member.dto.response.GetMemberResponse
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.domain.member.service.MemberInqueryService

@Service
class MemberInquiryServiceImpl(
    private val findAllMember: FindAllMember
) : MemberInqueryService {

    override fun getAllMember(
        id: Long?,
        stay: Boolean?,
        floor: Int?,
        room: Int?,
        grade: Int?,
        classNum: Int?
    ): List<GetMemberResponse> {
        val allMembers = findAllMember.findMemberByCache().map {   // 모든 Member 조회 후 GetMemberResponse로 변환
            GetMemberResponse(
                id = it.id,
                stay = it.stay,
                floor = it.floor,
                room = it.room,
                grade = it.grade,
                classNum = it.classNum,
                name = it.name,
                role = it.role,
                lateNumber = it.lateNumber,
                number = it.number
            )
        }
        val filteredMembers = allMembers.filter {  // 필터링
            (id == null || it.id == id) &&  // id가 null이 아니면 id가 같은지 확인
                    (stay == null || it.stay == stay) &&  // stay가 null이 아니면 stay가 같은지 확인
                    (floor == null || it.floor == floor) &&  // floor가 null이 아니면 floor가 같은지 확인
                    (room == null || it.room == room) &&  // room이 null이 아니면 room이 같은지 확인
                    (grade == null || it.grade == grade) &&  // grade가 null이 아니면 grade가 같은지 확인
                    (classNum == null || it.classNum == classNum)  // classNum이 null이 아니면 classNum이 같은지 확인
        }
        if (filteredMembers.isEmpty()) {  // 필터링된 Member가 없을 경우 예외 발생
            throw MemberNotFoundException("No member found")
        }
        return filteredMembers.map {  // 필터링된 Member를 GetMemberResponse로 변환
            GetMemberResponse(
                id = it.id,
                stay = it.stay,
                floor = it.floor,
                room = it.room,
                grade = it.grade,
                classNum = it.classNum,
                name = it.name,
                role = it.role,
                lateNumber = it.lateNumber,
                number = it.number
            )
        }
    }
}