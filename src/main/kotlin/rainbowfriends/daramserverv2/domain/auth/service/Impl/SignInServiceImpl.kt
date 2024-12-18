package rainbowfriends.daramserverv2.domain.auth.service.Impl

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.auth.dto.response.SigninOrReissueResponse
import rainbowfriends.daramserverv2.domain.auth.exception.EmailFormatException
import rainbowfriends.daramserverv2.domain.auth.exception.InvalidCodeException
import rainbowfriends.daramserverv2.domain.auth.service.SignInService
import rainbowfriends.daramserverv2.global.member.component.FindMember
import rainbowfriends.daramserverv2.global.member.component.SaveMember
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.enums.Roles
import rainbowfriends.daramserverv2.global.security.jwt.service.JwtTokenService
import rainbowfriends.daramserverv2.global.util.ParseStudentId
import java.util.*

@Service
class SignInServiceImpl(
    private val jwtTokenService: JwtTokenService,
    private val findMember: FindMember,
    private val saveMember: SaveMember,
    @Value("\${spring.security.oauth2.client.registration.google.client-id}")
    private val clientId: String,
    @Value("\${spring.security.oauth2.client.registration.google.client-secret}")
    private val clientSecret: String,
    @Value("\${spring.security.oauth2.client.registration.google.redirect-uri}")
    private val redirectUri: String
) : SignInService {
    override fun signIn(code: String): SigninOrReissueResponse {
        val email = authenticateWithCode(code)
        var member = findMember.findMemberByEmail(email)
        if (member == null) {
            member = updateMemberEmail(email)
        }
        val accessToken = jwtTokenService.generateAccessToken(email, member.role)
        val refreshToken = jwtTokenService.generateRefreshToken(email, member.role)
        return SigninOrReissueResponse(
            accessToken = accessToken.first,
            accessTokenExpiresIn = accessToken.second.toInstant().toString(),
            refreshToken = refreshToken.first,
            refreshTokenExpiresIn = refreshToken.second.toInstant().toString(),
            role = member.role
        )
    }

    private val httpTransport: NetHttpTransport = NetHttpTransport()
    private val jsonFactory: GsonFactory = GsonFactory()

    private fun authenticateWithCode(code: String): String {
        val tokenResponse: GoogleTokenResponse
        try {
            tokenResponse = GoogleAuthorizationCodeTokenRequest(
                httpTransport,
                jsonFactory,
                "https://oauth2.googleapis.com/token",
                clientId,
                clientSecret,
                code,
                redirectUri
            ).execute()
        } catch (e: Exception) {
            throw InvalidCodeException("Invalid Code: ${e.message}")
        }
        val idToken = verifyIdToken(tokenResponse.idToken)
            ?: throw IllegalArgumentException("Invalid ID Token")
        val payload = idToken.payload
        return payload["email"].toString()
    }

    private fun verifyIdToken(idTokenString: String): GoogleIdToken? {
        val verifier = GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
            .setAudience(listOf(clientId))
            .build()
        return verifier.verify(idTokenString)
    }

    private fun updateMemberEmail(email: String): Member {
        val emailHeader = email.split("@")[0]
        if (emailHeader.length != 6 || emailHeader[0] != 's') {
            throw EmailFormatException("Invalid email format")
        }
        val admissionYear = emailHeader.substring(1, 3).toInt()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100
        val grade = when (currentYear - admissionYear) {
            0 -> 1
            1 -> 2
            2 -> 3
            else -> throw EmailFormatException("Not a current student")
        }
        val studentNumber = emailHeader.substring(3).toInt()
        if (studentNumber !in 1..72) {
            throw EmailFormatException("Invalid student number")
        }
        val classroom = (studentNumber - 1) / 18 + 1
        val number = (studentNumber - 1) % 18 + 1
        val funking = funk(grade, number, classroom)
        var member: Member? = Member(id = null, email = email, role = Roles.USER, grade = funking.first, classNum = funking.second, number = funking.third, name = "null", floor = 2, lateNumber = 0, room = 301)
        if (funking.first != 0) {
            member = findMember.findMemberByGradeAndClassNumAndNumber(funking.first, funking.second, funking.third)
        }else {
            member = findMember.findMemberByGradeAndClassNumAndNumber(grade, classroom, number)
        }
        member!!.email = email
        return saveMember.saveMember(member)
    }
    private fun funk(grade: Int, number: Int,classNum:Int): Triple<Int,Int,Int> {
        val tempStudentId = String.format("%d%d%02d", grade, classNum, number).toInt()
        var result: Triple<Int, Int, Int> = Triple(0, 0, 0)
        if (tempStudentId >= 1109 && tempStudentId <= 1118) {
            result = Triple(1, 1, ParseStudentId.parseStudentId(tempStudentId.toString()).third - 1)
        }
        return result
    }
}