package rainbowfriends.daramserverv2.global.security.config

import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.stereotype.Component

@Component
class DomainAuthorizationConfig {
    fun configure(authorizeRequests: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry) {
        authorizeRequests  // HttpSecurity의 authorizeRequests 메서드를 사용하여 권한 설정
            .requestMatchers(  // requestMatchers 메서드를 사용하여 요청에 대한 권한 설정
                HttpMethod.GET,  // GET 메서드
                "/notice/all",
                "/notice/{id}",
                "/actuator/health",
                "/checkin/checkin",
                "/checkin/uncheckin",
            ).permitAll()  // 모든 사용자에게 허용
            .requestMatchers(  // requestMatchers 메서드를 사용하여 요청에 대한 권한 설정
                HttpMethod.POST,  // POST 메서드
                "/notice/**",
                "/camera/authorization"
            ).hasAnyRole("ADMIN", "TEACHER", "DEVELOPER")  // ADMIN, TEACHER, DEVELOPER 권한을 가진 사용자에게만 허용
            .requestMatchers(  // requestMatchers 메서드를 사용하여 요청에 대한 권한 설정
                HttpMethod.PUT,  // PUT 메서드
                "/notice/**"
            ).hasAnyRole("ADMIN", "TEACHER", "DEVELOPER")  // ADMIN, TEACHER, DEVELOPER 권한을 가진 사용자에게만 허용
            .requestMatchers(  // requestMatchers 메서드를 사용하여 요청에 대한 권한 설정
                HttpMethod.PATCH,  // PATCH 메서드
                "/notice/**"
            ).hasAnyRole("ADMIN", "TEACHER", "DEVELOPER")  // ADMIN, TEACHER, DEVELOPER 권한을 가진 사용자에게만 허용
            .requestMatchers(  // requestMatchers 메서드를 사용하여 요청에 대한 권한 설정
                HttpMethod.DELETE,  // DELETE 메서드
                "/notice/**"
            ).hasAnyRole("ADMIN", "TEACHER", "DEVELOPER")  // ADMIN, TEACHER, DEVELOPER 권한을 가진 사용자에게만 허용
            .requestMatchers(  // requestMatchers 메서드를 사용하여 요청에 대한 권한 설정
                "/actuator/**"
            ).hasRole("DEVELOPER")  // DEVELOPER 권한을 가진 사용자에게만 허용
            .requestMatchers(  // requestMatchers 메서드를 사용하여 요청에 대한 권한 설정
                "/checkin"
            ).hasAnyRole("ADMIN", "TEACHER", "DEVELOPER")  // ADMIN, TEACHER, DEVELOPER 권한을 가진 사용자에게만 허용
            .anyRequest().permitAll()  // 나머지 요청은 모든 사용자에게 허용
    }
}