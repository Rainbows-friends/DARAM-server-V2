package rainbowfriends.daramserverv2.domain.time.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.time.dto.enums.GetRemainTimeServiceAction
import rainbowfriends.daramserverv2.domain.time.dto.response.DetailedTimeResponse
import rainbowfriends.daramserverv2.domain.time.dto.response.SecondsResponse
import rainbowfriends.daramserverv2.domain.time.dto.response.TimeFormattedResponse
import rainbowfriends.daramserverv2.domain.time.service.RemainTimeService
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters

@Service
class RemainTimeServiceImpl : RemainTimeService {
    override fun getRemainTime(getRemainTimeServiceAction: GetRemainTimeServiceAction): Any {
        val nowTime = LocalDateTime.now()
        val weekdayLimitTime = nowTime.withHour(21).withMinute(30).withSecond(0)
        val nextWeekdayLimitTime = if (nowTime.isAfter(weekdayLimitTime)) {
            weekdayLimitTime.plusDays(1)
        } else {
            weekdayLimitTime
        }
        val nextSunday = nowTime.with(TemporalAdjusters.next(DayOfWeek.SUNDAY))
        val sundayTimes = listOf(
            LocalDateTime.of(nextSunday.year, nextSunday.month, nextSunday.dayOfMonth, 20, 20, 0),
            LocalDateTime.of(nextSunday.year, nextSunday.month, nextSunday.dayOfMonth, 20, 40, 0),
            LocalDateTime.of(nextSunday.year, nextSunday.month, nextSunday.dayOfMonth, 21, 0, 0)
        )
        val adjustedLimitTime = when (nowTime.dayOfWeek) {
            DayOfWeek.FRIDAY, DayOfWeek.SATURDAY -> sundayTimes[0]
            DayOfWeek.SUNDAY -> sundayTimes.firstOrNull { it.isAfter(nowTime) }
                ?: sundayTimes[0].plusWeeks(1)
            else -> nextWeekdayLimitTime
        }
        val duration = Duration.between(nowTime, adjustedLimitTime)
        return when (getRemainTimeServiceAction) {
            GetRemainTimeServiceAction.HOURS_MINUTES_SECONDS -> {
                val hours = duration.toHours()
                val minutes = duration.minusHours(hours).toMinutes()
                val seconds = duration.seconds % 60
                DetailedTimeResponse(adjustedLimitTime, hours, minutes, seconds)
            }
            GetRemainTimeServiceAction.STRING_FORMAT -> {
                val hours = duration.toHours()
                val minutes = duration.minusHours(hours).toMinutes()
                val seconds = duration.seconds % 60
                val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                TimeFormattedResponse(adjustedLimitTime, formattedTime)
            }
            GetRemainTimeServiceAction.SECONDS_ONLY -> {
                SecondsResponse(adjustedLimitTime, duration.seconds)
            }
        }
    }
}