package rainbowfriends.daramserverv2.domain.member.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.member.dto.request.PatchRoomRequest
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.domain.member.exception.PatchRoomException
import rainbowfriends.daramserverv2.domain.member.service.PatchRoomService
import rainbowfriends.daramserverv2.global.member.component.FindMember
import rainbowfriends.daramserverv2.global.member.component.SaveMember
import rainbowfriends.daramserverv2.global.util.ParseStudentId.parseStudentId

@Service
class PatchRoomServiceImpl(
    private val findMember: FindMember,
    private val saveMember: SaveMember
) : PatchRoomService {
    override fun patchRoom(request: PatchRoomRequest) {
        try {
            val (grade, classNum, number) = parseStudentId(request.studentId.toString())  // 학번을 학년, 반, 번호로 파싱
            val member = findMember.findMemberByGradeAndClassNumAndNumber(grade, classNum, number)  // 학년, 반, 번호로 Member 조회
                ?: throw MemberNotFoundException("Member not found for studentId: ${request.studentId}")  // Member가 없을 경우 예외 발생
            val updatedDto = member.toDto().copy(
                floor = request.room / 100,
                room = request.room.toInt()
            )  // DTO로 변환 후 copy 메서드를 이용해 room 정보 수정
            saveMember.saveMember(updatedDto.toEntity())  // 수정된 DTO를 Entity로 변환 후 저장
        } catch (e: IllegalArgumentException) {  // 학번이 잘못된 경우 예외 발생 처리
            throw PatchRoomException("Invalid student ID format: ${request.studentId}$e")
        } catch (e: MemberNotFoundException) {  // Member가 없는 경우 예외 발생 처리
            throw PatchRoomException("Member not found for studentId: ${request.studentId}$e")
        } catch (e: Exception) {  // 그 외 예외 발생 처리
            throw PatchRoomException("Unexpected error occurred while patching room$e")
        }
    }
}