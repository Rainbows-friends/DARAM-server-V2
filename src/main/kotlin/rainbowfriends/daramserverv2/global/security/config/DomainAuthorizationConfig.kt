package rainbowfriends.daramserverv2.global.security.config

import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.stereotype.Component

@Component
class DomainAuthorizationConfig {
    fun configure(authorizeRequests: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry) {
        authorizeRequests
            .requestMatchers(
                HttpMethod.GET,
                "/notice/all",
                "/notice/{id}"
            ).permitAll()
            .requestMatchers(
                HttpMethod.POST,
                "/notice/**"
            ).hasAnyRole("ADMIN", "TEACHER", "DEVELOPER")
            .requestMatchers(
                HttpMethod.PUT,
                "/notice/**"
            ).hasAnyRole("ADMIN", "TEACHER", "DEVELOPER")
            .requestMatchers(
                HttpMethod.PATCH,
                "/notice/**"
            ).hasAnyRole("ADMIN", "TEACHER", "DEVELOPER")
            .requestMatchers(
                HttpMethod.DELETE,
                "/notice/**"
            ).hasAnyRole("ADMIN", "TEACHER", "DEVELOPER")
            .requestMatchers(
                "/swagger-ui/**",
                "/actuator/**"
            ).hasRole("DEVELOPER")
            .requestMatchers(
                "/checkin"
            ).hasAnyRole("ADMIN", "TEACHER", "DEVELOPER")
            .anyRequest().permitAll()
    }
}