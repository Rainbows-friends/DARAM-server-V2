package rainbowfriends.daramserverv2.global.log

import org.slf4j.LoggerFactory

inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!  // Kotlin에서 Lombok의 @Slf4j와 같은 기능을 제공하는 함수