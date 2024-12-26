package rainbowfriends.daramserverv2.global.member.enums

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

enum class Roles : Authentication, GrantedAuthority {  // Spring Security에 권한을 등록하기 위한 Enum 클래스
    ADMIN {
        override fun getAuthorities() = setOf(this)  // 권한을 Set으로 반환(외부에서 수정 불가)
        override fun getCredentials() = null  // 자격 증명 반환(없음)
        override fun getDetails() = null  // 세부 정보 반환(없음)
        override fun getPrincipal() = this  // 주체 반환(자신)
        override fun isAuthenticated() = true  // 인증 여부 반환(인증됨)
        override fun setAuthenticated(isAuthenticated: Boolean) = Unit  // 인증 여부 설정(수정 불가)
        override fun getName() = "ADMIN"  // 이름 반환
    },
    DEVELOPER {
        override fun getAuthorities() = setOf(this)
        override fun getCredentials() = null
        override fun getDetails() = null
        override fun getPrincipal() = this
        override fun isAuthenticated() = true
        override fun setAuthenticated(isAuthenticated: Boolean) = Unit
        override fun getName() = "DEVELOPER"
    },
    TEACHER {
        override fun getAuthorities() = setOf(this)
        override fun getCredentials() = null
        override fun getDetails() = null
        override fun getPrincipal() = this
        override fun isAuthenticated() = true
        override fun setAuthenticated(isAuthenticated: Boolean) = Unit
        override fun getName() = "TEACHER"
    },
    USER {
        override fun getAuthorities() = setOf(this)
        override fun getCredentials() = null
        override fun getDetails() = null
        override fun getPrincipal() = this
        override fun isAuthenticated() = true
        override fun setAuthenticated(isAuthenticated: Boolean) = Unit
        override fun getName() = "USER"
    };

    override fun getAuthority(): String {
        return "ROLE_$name"  // 접두사 ROLE_을 붙여서 반환
    }
}