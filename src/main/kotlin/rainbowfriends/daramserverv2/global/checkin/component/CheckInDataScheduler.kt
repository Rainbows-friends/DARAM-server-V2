package rainbowfriends.daramserverv2.global.checkin.component

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import java.time.DayOfWeek
import java.time.LocalDate

@Component
class CheckInDataScheduler(
    private val checkInRepository: CheckInRepository,
    private val lateNumberUpdater: LateNumberUpdater,
    private val checkInPreparation: CheckInPreparation
) {

    @EventListener(ApplicationReadyEvent::class)  // 애플리케이션 실행 시(Spring Boot 시작 시) 실행
    fun initializeCheckInData() {  // 체크인 데이터 초기화
        val today = LocalDate.now()  // 오늘 날짜
        val tomorrow = today.plusDays(1)  // 내일 날짜
        checkInPreparation.prepareCheckInsForDate(today)  // 내일 날짜의 체크인 데이터 준비
        checkInPreparation.prepareCheckInsForDate(tomorrow)  // 모레 날짜의 체크인 데이터 준비
    }

    @Scheduled(cron = "0 35 21 * * *")  // 매일 21시 35분에 실행
    fun deleteOldCheckInData() {  // 오래된 체크인 데이터 삭제
        val cutoffDate = LocalDate.now().minusDays(2)  // 2일 전 날짜
        checkInRepository.deleteAll(checkInRepository.findByCheckinInfoDate(cutoffDate))  // 2일 전 날짜의 체크인 데이터 삭제
    }

    @Scheduled(cron = "0 40 21 * * SUN-THU")  // 매주 일요일부터 목요일까지 21시 40분에 실행
    fun scheduledCheckInDataSync() {  // 체크인 추가 및 지각 횟수 증가
        val today: LocalDate = LocalDate.now()  // 오늘 날짜
        val tomorrow: LocalDate = today.plusDays(1)  // 내일 날짜
        if (today.dayOfWeek != DayOfWeek.THURSDAY) {  // 오늘이 목요일이 아니라면
            checkInPreparation.prepareCheckInsForDate(tomorrow)  // 내일 날짜의 체크인 데이터 준비
        } else {  // 오늘이 목요일이라면
            return   // 종료
        }
        lateNumberUpdater.lateNumberRaise(today)  // 오늘이 목요일이 아니라면 지각 횟수 증가
    }
}