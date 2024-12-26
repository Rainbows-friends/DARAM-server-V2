package rainbowfriends.daramserverv2.domain.checkin.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.checkin.service.CheckInTransaction
import rainbowfriends.daramserverv2.global.redis.redisson.util.DistributedLock
import rainbowfriends.daramserverv2.global.util.ParseStudentId.parseStudentId
import java.time.LocalDate

@Component
class CheckInStatusSwitch(
    private val checkInTransaction: CheckInTransaction,
    private val distributedLock: DistributedLock
) {

    fun switchCheckInStatus(studentId: Short?, date: LocalDate): Boolean {  // Reddisson을 사용하여 분산 락을 걸어서 체크인 상태를 변경하는 함수
        if (studentId == null) return false  // 학생 ID가 없으면 false 반환(처리 실패)
        val lockKey = "check-in:$date"  // Lock Key를 설정
        return distributedLock.executeWithLock(lockKey, 5L, 10L) {  // Lock을 걸고 5초 동안 대기하고 10초 동안 Lock을 유지
            handleCheckInStatusSwitch(studentId, date)  // 체크인 상태를 변경하는 함수를 호출
        }
    }

    private fun handleCheckInStatusSwitch(studentId: Short, date: LocalDate): Boolean {
        return try {
            val parsedStudentId = parseStudentId(studentId.toString())  // 학번을 파싱(ParsedStudentId Object로 반환)
            val user = checkInTransaction.getMemberDto(parsedStudentId.first, parsedStudentId.second, parsedStudentId.third)  // 학번을 이용하여 사용자 정보를 가져옴
            val checkIn = checkInTransaction.getCheckInRecord(user, date)  // 사용자 정보와 날짜를 이용하여 체크인 정보를 가져옴
            checkInTransaction.checkInDateModify(checkIn)  // 입실 확인 시각을 기록
            checkInTransaction.toggleCheckInStatus(checkIn)  // 체크인 상태를 변경
            true  // 여기까지 오류없이 실행되면 true 반환
        } catch (_: Exception) {  // 매개변수가 필요없으므로 _로 처리
            false  // 오류가 발생하면 false 반환
        }
    }
}
