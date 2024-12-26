package rainbowfriends.daramserverv2.domain.time.config

import java.time.DayOfWeek
import java.time.LocalDateTime

object DeadlineConfig {
    fun getWeekdayDeadline(now: LocalDateTime): LocalDateTime =  // 월,화,수,목의 기숙사 입사 마감시간
        now.withHour(21).withMinute(30).withSecond(0).withNano(0)

    fun getSundayDeadlines(now: LocalDateTime): List<LocalDateTime> = listOf(  // 일요일의 기숙사 입사 마감시간
        now.with(DayOfWeek.SUNDAY).withHour(20).withMinute(20).withSecond(0).withNano(0),
        now.with(DayOfWeek.SUNDAY).withHour(20).withMinute(40).withSecond(0).withNano(0),
        now.with(DayOfWeek.SUNDAY).withHour(21).withMinute(0).withSecond(0).withNano(0)
    )
}