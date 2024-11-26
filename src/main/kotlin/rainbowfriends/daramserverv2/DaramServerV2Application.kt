package rainbowfriends.daramserverv2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
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
)
@EnableScheduling
@EnableMongoRepositories(basePackages = ["rainbowfriends.daramserverv2.global.checkin.repository"])
class DaramServerV2Application

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    runApplication<DaramServerV2Application>(*args)
}