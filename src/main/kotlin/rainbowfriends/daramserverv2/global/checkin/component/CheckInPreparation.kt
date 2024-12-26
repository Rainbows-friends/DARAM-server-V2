package rainbowfriends.daramserverv2.global.checkin.component

import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository
import java.time.LocalDate

@Component
class CheckInPreparation(
    private val checkInRepository: CheckInRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional  // 트랜잭션 처리 적용
    fun prepareCheckInsForDate(date: LocalDate) {
        val allMember: List<Member> = memberRepository.findAll()  // 모든 회원 조회
        if (checkInRepository.findByCheckinInfoDate(date).isEmpty()) {  // 해당 날짜의 체크인 데이터가 없으면
            val checkIns: List<CheckIn> = allMember.map { member ->  // 모든 회원에 대해 체크인 데이터 생성, Map을 사용하여 List로 변환
                CheckIn(
                    id = null,  // ID는 null로 설정(자동 생성)
                    user = member,  // 회원 정보 설정
                    checkinInfoDate = date,  // 체크인 날짜 설정(매개변수로 받아온 날짜)
                    checkinStatus = false  // 체크인 상태는 false로 설정
                )
            }
            checkInRepository.saveAll(checkIns)  // 생성한 체크인 데이터 저장
        }
    }
}