package rainbowfriends.daramserverv2.global.security.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Component
class CorsConfig(@Value("\${production.front-url}") val productionFrontUrl: String) {
    @Bean
    fun configureCors(): UrlBasedCorsConfigurationSource {
        val config = CorsConfiguration().apply {  // CorsConfiguration 객체 생성(apply 함수를 사용하여 주어진 람다식을 객체에 적용)
            allowCredentials = true  // 인증 정보 허용 여부 설정
            allowedOrigins =  // 허용된 Origin 설정
                listOf("http://localhost:5173",  // Vite 개발 서버
                    productionFrontUrl,  // 실제 제품 서버
                    "http://localhost:3000" // React 개발 서버
                )
            allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 허용된 HTTP 메소드 설정
            allowedHeaders = listOf("Authorization", "Content-Type","C")  // 허용된 헤더 설정
        }
        val source = UrlBasedCorsConfigurationSource()  // UrlBasedCorsConfigurationSource(Url 기반 CorsConfiguration 설정) 객체 생성
        source.registerCorsConfiguration("/**", config)  // 모든 URL에 CorsConfiguration 설정 적용
        return source
    }
}