package rainbowfriends.daramserverv2.domain.time.service.impl

import org.springframework.stereotype.Service
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
    private val weekdayDeadline = LocalDateTime.now().withHour(21).withMinute(30).withSecond(0).withNano(0)
    private val sundayDeadlines = listOf(
        LocalDateTime.now().withHour(20).withMinute(20).withSecond(0).withNano(0),
        LocalDateTime.now().withHour(20).withMinute(40).withSecond(0).withNano(0),
        LocalDateTime.now().withHour(21).withMinute(0).withSecond(0).withNano(0)
    )
    private val weekdayDeadlines = mapOf(
        DayOfWeek.MONDAY to weekdayDeadline,
        DayOfWeek.TUESDAY to weekdayDeadline,
        DayOfWeek.WEDNESDAY to weekdayDeadline,
        DayOfWeek.THURSDAY to weekdayDeadline
    )

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
        val dayOfWeek = now.dayOfWeek
        return when (dayOfWeek) {
            DayOfWeek.SUNDAY -> {
                sundayDeadlines.minByOrNull { deadline ->
                    ChronoUnit.SECONDS.between(now, deadline)
                }?.takeIf { ChronoUnit.SECONDS.between(now, it) >= 0 }
                    ?: LocalDateTime.now().plusDays(1).with(DayOfWeek.MONDAY).withHour(21).withMinute(30)
            }

            in listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY) -> {
                val targetDeadline = weekdayDeadline
                if (ChronoUnit.SECONDS.between(now, targetDeadline) < 0) {
                    targetDeadline.plusDays(1)
                } else {
                    targetDeadline
                }
            }

            DayOfWeek.THURSDAY -> {
                sundayDeadlines.first()
            }

            in listOf(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY) -> {
                sundayDeadlines.first()
            }

            else -> {
                LocalDateTime.now().plusDays(1).with(DayOfWeek.MONDAY).withHour(21).withMinute(30)
            }
        }
    }
}