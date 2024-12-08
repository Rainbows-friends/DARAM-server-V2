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
        val now = LocalDateTime.now()
        val closestDeadline = findClosestDeadline(now)
        val remainTime = ChronoUnit.SECONDS.between(now, closestDeadline)
        return when (getRemainTimeServiceAction) {
            GetRemainTimeServiceAction.HOURS_MINUTES_SECONDS -> {
                val hours = remainTime / 3600
                val minutes = (remainTime % 3600) / 60
                val seconds = remainTime % 60
                DetailedTimeResponse(closestDeadline, hours, minutes, seconds)
            }

            GetRemainTimeServiceAction.STRING_FORMAT -> {
                val hours = remainTime / 3600
                val minutes = (remainTime % 3600) / 60
                val seconds = remainTime % 60
                TimeFormattedResponse(closestDeadline, String.format("%02d:%02d:%02d", hours, minutes, seconds))
            }

            GetRemainTimeServiceAction.SECONDS_ONLY -> {
                SecondsResponse(closestDeadline, remainTime)
            }
        }
    }

    private fun findClosestDeadline(now: LocalDateTime): LocalDateTime {
        val weekdayDeadline = DeadlineConfig.getWeekdayDeadline(now)
        val sundayDeadlines = DeadlineConfig.getSundayDeadlines(now)
        val dayOfWeek = now.dayOfWeek
        return when (dayOfWeek) {
            DayOfWeek.SUNDAY -> {
                val nextDeadline = sundayDeadlines
                    .filter { ChronoUnit.SECONDS.between(now, it) >= 0 }
                    .minByOrNull { ChronoUnit.SECONDS.between(now, it) }
                nextDeadline ?: now.plusDays(1).withHour(21).withMinute(30).withSecond(0).withNano(0)
            }
            in listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY) -> {
                if (ChronoUnit.SECONDS.between(now, weekdayDeadline) < 0) {
                    weekdayDeadline.plusDays(1)
                } else {
                    weekdayDeadline
                }
            }
            DayOfWeek.THURSDAY -> {
                if (ChronoUnit.SECONDS.between(now, weekdayDeadline) < 0) {
                    sundayDeadlines.first()
                } else {
                    weekdayDeadline
                }
            }
            DayOfWeek.FRIDAY -> {
                sundayDeadlines.first()
            }
            DayOfWeek.SATURDAY -> {
                sundayDeadlines.first()
            }
            else -> {
                now.plusDays(1).withHour(21).withMinute(30).withSecond(0).withNano(0)
            }
        }
    }
}