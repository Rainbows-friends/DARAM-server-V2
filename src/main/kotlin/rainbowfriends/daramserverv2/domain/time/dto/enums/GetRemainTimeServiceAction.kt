package rainbowfriends.daramserverv2.domain.time.dto.enums

enum class GetRemainTimeServiceAction {
    HOURS_MINUTES_SECONDS, STRING_FORMAT, SECONDS_ONLY;

    companion object {  // Java Enum의 valueOf와 같은 기능
        fun from(value: String): GetRemainTimeServiceAction {  // String을 Enum으로 변환
            return valueOf(value.replace("-", "_").uppercase())  // Enum의 이름과 일치하는 String을 Enum으로 변환
        }
    }
}