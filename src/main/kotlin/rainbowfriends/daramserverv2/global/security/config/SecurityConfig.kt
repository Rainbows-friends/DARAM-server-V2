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
    ): SecurityFilterChain {  // SecurityFilterChain 빈 생성
        http  // HttpSecurity 설정
            .cors { cors -> cors.configurationSource(corsConfig.configureCors()) }  // CORS 설정(설정된 CorsConfig를 사용)
            .csrf { csrf -> csrf.disable() }  // CSRF(사이트간 요청 위조) 설정 비활성화
            .formLogin { form -> form.disable() }  // Form 로그인 설정 비활성화
            .oauth2Login {  // OAuth2 로그인 설정
                it.loginPage("/oauth2/authorization/google")  // OAuth2 로그인 페이지 설정(해당 URL로 접속 시 OAuth2 로그인 페이지로 리다이렉트)
                    .successHandler { request, response, authentication ->  // 로그인 성공 시 핸들러
                        response.sendRedirect("/success")  // 로그인 성공 시 /success로 리다이렉트
                    }
                    .failureHandler { request, response, exception ->  // 로그인 실패 시 핸들러
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed")  // 로그인 실패 시 401 에러 반환
                    }
            }
            .sessionManagement { session ->  // 세션 관리 설정
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 세션 생성 정책 설정(STATELESS: 세션 사용 안함)
            }
            .addFilterBefore(  // JwtFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
                JwtFilter(jwtTokenParserService),
                UsernamePasswordAuthenticationFilter::class.java  // UsernamePasswordAuthenticationFilter(기본 로그인 필터) 앞에 추가
            )
            .authorizeHttpRequests {  // 요청 권한 설정
                domainAuthorizationConfig.configure(it)
            }
            .exceptionHandling { exceptions ->  // 예외 처리 설정
                exceptions.authenticationEntryPoint { request, response, authException ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")  // 인증되지 않은 사용자의 요청 시 401 에러 반환
                }
                exceptions.accessDeniedHandler { request, response, accessDeniedException ->  // 접근 거부 시 핸들러
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied")  // 접근 걱부 시 403 에러 반환
                }
            }
        return http.build()  // HttpSecurity 설정 빌드하여 반환
    }
}