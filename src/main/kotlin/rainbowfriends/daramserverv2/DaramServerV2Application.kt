package rainbowfriends.daramserverv2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@SpringBootApplication
@EnableRedisRepositories(basePackages = ["rainbowfriends.daramserverv2.global.security.repository"])
@EntityScan(basePackages = ["rainbowfriends.daramserverv2.global.security.key.entity"])
class DaramServerV2Application
fun main(args: Array<String>) {
        runApplication<DaramServerV2Application>(*args)
}