package rainbowfriends.daramserverv2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication
@EntityScan(
    basePackages = [
        "rainbowfriends.daramserverv2.global.security.key.entity",
        "rainbowfriends.daramserverv2.domain.notice.entity",
        "rainbowfriends.daramserverv2.global.member.entity",
        "rainbowfriends.daramserverv2.global.checkin.entity"
    ]
)
class DaramServerV2Application

fun main(args: Array<String>) {
    runApplication<DaramServerV2Application>(*args)
}