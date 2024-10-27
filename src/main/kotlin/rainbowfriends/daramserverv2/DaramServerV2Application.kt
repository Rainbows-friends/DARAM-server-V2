package rainbowfriends.daramserverv2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication
@EntityScan(basePackages = ["rainbowfriends.daramserverv2.global.security.key.entity", "rainbowfriends.daramserverv2.domain.notice.entity"])
class DaramServerV2Application

fun main(args: Array<String>) {
    runApplication<DaramServerV2Application>(*args)
}