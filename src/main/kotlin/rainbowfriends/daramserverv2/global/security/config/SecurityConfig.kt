package rainbowfriends.daramserverv2.global.security.config

import jakarta.servlet.Filter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import rainbowfriends.daramserverv2.global.security.filter.TokenFilter
import rainbowfriends.daramserverv2.global.security.token.TokenService

@Configuration
class SecurityConfig(
    private val domainAuthorizationConfig: DomainAuthorizationConfig,
    private val corsConfig: CorsConfig,
) {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity, jwtTokenService: TokenService
    ): SecurityFilterChain {
        http
            .cors { cors -> cors.configurationSource(corsConfig.configureCors()) }
            .csrf { csrf -> csrf.disable() }
            .formLogin { form -> form.disable() }
            .addFilterBefore(
                TokenFilter(jwtTokenService) as Filter,
                UsernamePasswordAuthenticationFilter::class.java)
            .authorizeHttpRequests { domainAuthorizationConfig.configure(it) }
        return http.build()
    }
}