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
    fun lateNumberRaise(date: LocalDate) {
        try {
            checkInRepository.findByCheckinInfoDate(date).forEach { checkIn ->  // foreach를 사용하여 금일 모든 체크인 데이터에 대해 반복
                if (!checkIn.checkinStatus) {  // 체크인 상태가 false인 경우(체크인하지 않은 경우)
                    val member = memberRepository.findById(checkIn.id!!)  // 체크인 데이터의 회원 ID로 회원 조회
                        .orElseThrow { MemberNotFoundException("Member not found") }
                    member.lateNumber = member.lateNumber?.plus(1)  // 지각 횟수 1 증가
                    memberRepository.save(member)  // 회원 정보 저장
                }
            }
        } catch (_: Exception) {  // 매개변수가 필요 하지 않으므로 _ 사용
            throw LateNumberRaiseFailException("Late Number Raise failed")  // 지각 횟수 업데이트 실패 시 예외 발생
        }
    }
}