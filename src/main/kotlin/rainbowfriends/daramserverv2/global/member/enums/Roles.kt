package rainbowfriends.daramserverv2.global.member.enums

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

enum class Roles : Authentication, GrantedAuthority {
    ADMIN {
        override fun getAuthorities() = setOf(this)
        override fun getCredentials() = null
        override fun getDetails() = null
        override fun getPrincipal() = this
        override fun isAuthenticated() = true
        override fun setAuthenticated(isAuthenticated: Boolean) = Unit
        override fun getName() = "ADMIN"
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
        return name
    }
}