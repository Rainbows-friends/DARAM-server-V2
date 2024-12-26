package rainbowfriends.daramserverv2.domain.checkin.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import rainbowfriends.daramserverv2.global.member.dto.MemberDTO
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository
import java.time.LocalDate
import java.time.LocalDateTime

/*
Made by 태은
Refactor by 재욱
 */
@Service
class CheckInTransaction(
    private val checkInRepository: CheckInRepository,
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun getMemberDto(grade: Int, classNum: Int, studentNum: Int): MemberDTO {  // 학년, 반, 학번을 이용하여 사용자 정보를 가져오는 함수
        return memberRepository.findByGradeAndClassNumAndNumber(grade, classNum, studentNum)!!.toDto()
    }

    @Transactional(readOnly = true)  // 읽기 전용 트랜잭션
    fun getCheckInRecord(user: MemberDTO, date: LocalDate): CheckIn {  // 사용자 정보와 날짜를 이용하여 체크인 정보를 가져오는 함수
        val user: Member? = memberRepository.findByGradeAndClassNumAndNumber(user.grade, user.classNum, user.number)
        return checkInRepository.findByUserAndCheckinInfoDate(user!!, date)  // 논리적으로 user는 null이 아니므로 !! 사용
    }

    @Transactional
    fun toggleCheckInStatus(checkIn: CheckIn) {  // 체크인 상태를 변경하는 함수
        checkIn.checkinStatus = !checkIn.checkinStatus  // 체크인 상태를 반전
        checkInRepository.save(checkIn)  // 변경된 체크인 정보를 저장
    }

    @Transactional
    fun checkInDateModify(checkIn: CheckIn) {  // 입실 확인 시각을 기록하는 함수
        if (checkIn.checkinStatus) {  // 체크인 상태가 true이면
            checkIn.checkinDate = null  // 함수 실행 완료시 false가 되므로 입실 확인 시각을 null로 설정
        } else {  // 체크인 상태가 false이면
            checkIn.checkinDate = LocalDateTime.now()  // 현재 시각을 현재 시각으로 설정
        }
        checkInRepository.save(checkIn)  // 변경된 체크인 정보를 저장
    }
}