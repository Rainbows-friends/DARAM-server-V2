package rainbowfriends.daramserverv2.global.event

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
object ApplicationContextProvider : ApplicationContextAware {
    lateinit var context: ApplicationContext
        private set

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }
}