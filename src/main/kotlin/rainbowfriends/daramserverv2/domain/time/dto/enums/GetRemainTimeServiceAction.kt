package rainbowfriends.daramserverv2.domain.time.dto.enums

enum class GetRemainTimeServiceAction {
    HOURS_MINUTES_SECONDS, STRING_FORMAT, SECONDS_ONLY;

    companion object {
        fun from(value: String): GetRemainTimeServiceAction {
            return valueOf(value.replace("-", "_").uppercase())
        }
    }
}