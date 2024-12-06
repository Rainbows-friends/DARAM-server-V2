package rainbowfriends.daramserverv2.domain.time.config

import java.time.DayOfWeek
import java.time.LocalDateTime

object DeadlineConfig {
    fun getWeekdayDeadline(now: LocalDateTime): LocalDateTime =
        now.withHour(21).withMinute(30).withSecond(0).withNano(0)

    fun getSundayDeadlines(now: LocalDateTime): List<LocalDateTime> = listOf(
        now.with(DayOfWeek.SUNDAY).withHour(20).withMinute(20).withSecond(0).withNano(0),
        now.with(DayOfWeek.SUNDAY).withHour(20).withMinute(40).withSecond(0).withNano(0),
        now.with(DayOfWeek.SUNDAY).withHour(21).withMinute(0).withSecond(0).withNano(0)
    )
}