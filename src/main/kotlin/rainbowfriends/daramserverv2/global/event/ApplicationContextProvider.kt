package rainbowfriends.daramserverv2.global.event

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
object ApplicationContextProvider : ApplicationContextAware {  // ApplicationContext 제공자
    lateinit var context: ApplicationContext  // ApplicationContext 변수 선언,
        private set  // private set으로 외부에서 수정 불가

    override fun setApplicationContext(applicationContext: ApplicationContext) {  // ApplicationContext 설정
        context = applicationContext  // ApplicationContext 설정
    }
}