package rainbowfriends.daramserverv2

import jakarta.annotation.PostConstruct
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
@EnableMongoRepositories(basePackages = ["rainbowfriends.daramserverv2.global.checkin.repository"])
@EnableScheduling
class DaramServerV2Application

fun main(args: Array<String>) {
    runApplication<DaramServerV2Application>(*args)
    @PostConstruct
    fun init() {
        var timeZone = System.getenv("TZ");
        if (timeZone == null || timeZone.isEmpty()) {
            timeZone = "Asia/Seoul";
        }
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
        System.out.println("Application running with timezone: " + timeZone);
    }
}