package rainbowfriends.daramserverv2.domain.time.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.time.config.DeadlineConfig
import rainbowfriends.daramserverv2.domain.time.dto.enums.GetRemainTimeServiceAction
import rainbowfriends.daramserverv2.domain.time.dto.response.DetailedTimeResponse
import rainbowfriends.daramserverv2.domain.time.dto.response.SecondsResponse
import rainbowfriends.daramserverv2.domain.time.dto.response.TimeFormattedResponse
import rainbowfriends.daramserverv2.domain.time.service.RemainTimeService
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class RemainTimeServiceImpl : RemainTimeService {
    override fun getRemainTime(getRemainTimeServiceAction: GetRemainTimeServiceAction): Any {
        val now = LocalDateTime.now()  // 현재 서버 시간
        val closestDeadline = findClosestDeadline(now)  // 가장 가까운 마감 시간
        val remainTime = ChronoUnit.SECONDS.between(now, closestDeadline)  // 현재 시간과 가장 가까운 마감 시간과의 차이
        return when (getRemainTimeServiceAction) {  // 요청에 따라 응답 DTO를 생성하여 반환
            GetRemainTimeServiceAction.HOURS_MINUTES_SECONDS -> {  // 시간, 분, 초 단위로 시간을 표현하는 경우
                val hours = remainTime / 3600  // remainTime을 3600으로 나눈 몫(시간)
                val minutes = (remainTime % 3600) / 60  // remainTime을 3600으로 나눈 나머지를 60으로 나눈 몫(분)
                val seconds = remainTime % 60   // remainTime을 60으로 나눈 나머지(초)
                DetailedTimeResponse(closestDeadline, hours, minutes, seconds)  // 응답 DTO 생성
            }

            GetRemainTimeServiceAction.STRING_FORMAT -> {  // 시간을 포맷팅하여 표현하는 경우
                val hours = remainTime / 3600  // remainTime을 3600으로 나눈 몫(시간)
                val minutes = (remainTime % 3600) / 60  // remainTime을 3600으로 나눈 나머지를 60으로 나눈 몫(분)
                val seconds = remainTime % 60  // remainTime을 60으로 나눈 나머지(초)
                TimeFormattedResponse(closestDeadline, String.format("%02d:%02d:%02d", hours, minutes, seconds))
            }

            GetRemainTimeServiceAction.SECONDS_ONLY -> {  // 초 단위로 시간을 표현하는 경우
                SecondsResponse(closestDeadline, remainTime)  // 응답 DTO 생성
            }
        }
    }

    private fun findClosestDeadline(now: LocalDateTime): LocalDateTime {  // 가장 가까운 마감 시간을 찾는 함수
        val weekdayDeadline = DeadlineConfig.getWeekdayDeadline(now)  // 평일 마감 시간
        val sundayDeadlines = DeadlineConfig.getSundayDeadlines(now)  // 일요일 마감 시간
        val dayOfWeek = now.dayOfWeek  // 현재 요일
        return when (dayOfWeek) {   // 현재 요일에 따라 가장 가까운 마감 시간을 반환
            DayOfWeek.SUNDAY -> {  // 일요일인 경우
                val nextDeadline = sundayDeadlines  // 일요일 마감 시간 중 현재 시간 이후인 시간 중 가장 빠른 시간
                    .filter { ChronoUnit.SECONDS.between(now, it) >= 0 }  // 현재 시간 이후인 시간만 필터링
                    .minByOrNull { ChronoUnit.SECONDS.between(now, it) }  // 가장 빠른 시간을 찾음
                nextDeadline ?: now.plusDays(1).withHour(21).withMinute(30).withSecond(0)
                    .withNano(0)  // 가장 빠른 시간이 없으면 다음날 21시 30분으로 설정
            }

            in listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY) -> {  // 월요일, 화요일, 수요일인 경우
                if (ChronoUnit.SECONDS.between(now, weekdayDeadline) < 0) {  // 평일 마감 시간이 현재 시간 이전인 경우
                    weekdayDeadline.plusDays(1)  // 다음날 마감 시간으로 설정
                } else {
                    weekdayDeadline  // 평일 마감 시간으로 설정
                }
            }

            DayOfWeek.THURSDAY -> {  // 목요일인 경우
                if (ChronoUnit.SECONDS.between(now, weekdayDeadline) < 0) {  // 평일 마감 시간이 현재 시간 이전인 경우
                    sundayDeadlines.first()  // 일요일 마감 시간 중 가장 빠른 시간으로 설정
                } else {  // 평일 마감 시간이 현재 시간 이후인 경우
                    weekdayDeadline  // 평일 마감 시간으로 설정
                }
            }

            DayOfWeek.FRIDAY -> {  // 금요일인 경우
                sundayDeadlines.first()  // 일요일 마감 시간 중 가장 빠른 시간으로 설정
            }

            DayOfWeek.SATURDAY -> {  // 토요일인 경우
                sundayDeadlines.first()  // 일요일 마감 시간 중 가장 빠른 시간으로 설정
            }

            else -> {  // 그 외의 경우
                now.plusDays(1).withHour(21).withMinute(30).withSecond(0).withNano(0)  // 다음날 21시 30분으로 설정
            }
        }
    }
}