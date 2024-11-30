package rainbowfriends.daramserverv2.global.security.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Component
class CorsConfig(
    @Value("\${production.url}")
    val productionUrl: String,
    @Value("\${production.front-url}")
    val productionFrontUrl: String
) {
    @Bean
    fun configureCors(): UrlBasedCorsConfigurationSource {
        val config = CorsConfiguration().apply {
            allowCredentials = true
            allowedOrigins =
                listOf(
                    "http://localhost:5173",
                    "http://localhost:8080",
                    "https://daram-gsm.kro.kr",
                    productionUrl,
                    productionFrontUrl
                )
            allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
            allowedHeaders = listOf("Authorization", "Content-Type")
        }
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }
}