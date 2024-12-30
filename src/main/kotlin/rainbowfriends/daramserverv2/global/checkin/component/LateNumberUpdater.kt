package rainbowfriends.daramserverv2.global.checkin.component

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.global.checkin.exception.LateNumberRaiseFailException
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository
import java.time.LocalDate

@Component
class LateNumberUpdater(
    private val checkInRepository: CheckInRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun lateNumberRaise(date: LocalDate) {  // 지각 횟수 증가
        try {
            val checkIns = checkInRepository.findByCheckinInfoDate(date)  // 해당 날짜의 체크인 데이터 조회
                .filter { !it.checkinStatus }  // 체크인 상태가 false인 데이터만 필터링
            val memberIds = checkIns.mapNotNull { it.user.id }  // 체크인 데이터에서 사용자 ID 추출
            val members = memberRepository.findAllById(memberIds).associateBy { it.id }  // 사용자 ID로 사용자 조회
            checkIns.forEach { checkIn ->  // 체크인 데이터 반복
                val memberId = checkIn.user.id  // 사용자 ID
                val member = members[memberId]  // 사용자 ID를 인덱스로 활용하여 사용자 조회
                if (member == null) {  // 사용자가 없다면
                    throw MemberNotFoundException("Member with ID $memberId not found")
                }
                val previousLateNumber = member.lateNumber ?: 0  // 이전 지각 횟수(없다면 0)
                member.lateNumber = previousLateNumber + 1  // 지각 횟수 증가
            }
            memberRepository.saveAll(members.values)  // 사용자 저장
        } catch (e: MemberNotFoundException) {  // 사용자가 없다면
            throw LateNumberRaiseFailException("Late Number Raise failed: ${e.message}" + e)
        } catch (e: Exception) {  // 그 외 예외 발생 시
            throw LateNumberRaiseFailException("Late Number Raise failed" + e)
        }
    }
}