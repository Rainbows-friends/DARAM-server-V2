package rainbowfriends.daramserverv2.global.security.config

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.stereotype.Component

@Component
class DomainAuthorizationConfig {
    fun configure(authorizeRequests: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry) {
        authorizeRequests
            .requestMatchers(
                "/swagger-ui/**",
                "/graphiql/**",
                "/actuator/**",
            ).hasRole("DEVELOPER")
            .requestMatchers(
                "/notice/**",
                "/checkin",
            ).hasAnyRole("ADMIN", "TEACHER", "DEVELOPER")
            .requestMatchers(
                "/auth",
                "/time",
                "/graphql",
                "/checkin/checkin",
                "/checkin/uncheckin",
                "/member/**"
            ).permitAll()
            .anyRequest().permitAll()
    }
}