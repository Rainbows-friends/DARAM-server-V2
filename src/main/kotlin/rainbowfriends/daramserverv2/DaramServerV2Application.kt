package rainbowfriends.daramserverv2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.*

@SpringBootApplication
@EntityScan(
    basePackages = [
        "rainbowfriends.daramserverv2.global.security.key.entity",
        "rainbowfriends.daramserverv2.domain.notice.entity",
        "rainbowfriends.daramserverv2.global.member.entity",
        "rainbowfriends.daramserverv2.global.checkin.entity"
    ]
)  // Entity 클래스가 있는 패키지를 지정
@EnableScheduling  // 스케줄링 활성화
class DaramServerV2Application

fun main(args: Array<String>) {

    runApplication<DaramServerV2Application>(*args)
}