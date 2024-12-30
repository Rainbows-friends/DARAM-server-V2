package rainbowfriends.daramserverv2.global.security.config

import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.stereotype.Component

@Component
class DomainAuthorizationConfig {
    fun configure(authorizeRequests: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry) {
        authorizeRequests
            // `/oauth2/authorization/google` 요청 허용
            .requestMatchers("/oauth2/authorization/google").permitAll()
            // `/auth`로 시작하는 모든 요청 허용
            .requestMatchers("/auth/**").permitAll()
            // `/time`로 시작하는 모든 요청 허용
            .requestMatchers("/time/**").permitAll()
            // `/notice`로 시작하는 요청
            .requestMatchers(HttpMethod.GET, "/notice/**").permitAll() // GET 요청은 모두 허용
            .requestMatchers(HttpMethod.POST, "/notice/**").hasAnyRole("ADMIN", "TEACHER", "DEVELOPER") // POST 요청은 제한
            .requestMatchers(HttpMethod.PUT, "/notice/**").hasAnyRole("ADMIN", "TEACHER", "DEVELOPER")  // PUT 요청은 제한
            .requestMatchers(HttpMethod.PATCH, "/notice/**").hasAnyRole("ADMIN", "TEACHER", "DEVELOPER") // PATCH 요청은 제한
            .requestMatchers(HttpMethod.DELETE, "/notice/**").hasAnyRole("ADMIN", "TEACHER", "DEVELOPER") // DELETE 요청은 제한
            // `/checkin`으로 시작하는 요청
            .requestMatchers("/checkin/**").permitAll() // 모든 요청은 허용
            .requestMatchers(HttpMethod.PATCH, "/checkin/**").hasAnyRole("ADMIN", "TEACHER", "DEVELOPER") // PATCH 요청은 제한
            // `/member`로 시작하는 요청
            .requestMatchers(HttpMethod.GET, "/member/**").permitAll() // GET 요청은 모두 허용
            .requestMatchers(HttpMethod.PATCH, "/member/**").hasAnyRole("ADMIN", "TEACHER", "DEVELOPER") // PATCH 요청은 제한
            // 나머지 요청은 모두 허용
            .anyRequest().permitAll()
    }
}