package rainbowfriends.daramserverv2.global.security.config

import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import rainbowfriends.daramserverv2.global.security.jwt.filter.JwtFilter
import rainbowfriends.daramserverv2.global.security.jwt.service.JwtTokenParserService

@Configuration
class SecurityConfig(
    private val domainAuthorizationConfig: DomainAuthorizationConfig,
    private val corsConfig: CorsConfig,
) {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity, jwtTokenParserService: JwtTokenParserService
    ): SecurityFilterChain {
        http
            .cors { cors -> cors.configurationSource(corsConfig.configureCors()) }
            .csrf { csrf -> csrf.disable() }
            .formLogin { form -> form.disable() }
            .oauth2Login {
                it.loginPage("/oauth2/authorization/google")
                    .successHandler { request, response, authentication ->
                        response.sendRedirect("/success")
                    }
                    .failureHandler { request, response, exception ->
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed")
                    }
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(
                JwtFilter(jwtTokenParserService),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .authorizeHttpRequests {
                domainAuthorizationConfig.configure(it)
            }
            .exceptionHandling { exceptions ->
                exceptions.authenticationEntryPoint { request, response, authException ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                }
                exceptions.accessDeniedHandler { request, response, accessDeniedException ->
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied")
                }
            }
        return http.build()
    }
}