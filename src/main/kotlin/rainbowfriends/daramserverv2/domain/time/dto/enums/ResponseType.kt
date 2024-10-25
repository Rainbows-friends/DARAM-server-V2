package rainbowfriends.daramserverv2.domain.time.dto.enums

enum class ResponseType {
    HOURS_MINUTES_SECONDS, STRING_FORMAT, SECONDS_ONLY;

    companion object {
        fun from(value: String): ResponseType {
            return valueOf(value.replace("-", "_").uppercase())
        }
    }
}