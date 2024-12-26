package rainbowfriends.daramserverv2.domain.auth.service.impl

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
        val email = authenticateWithCode(code)  // Private 메서드 authenticateWithCode 호출
        var member = findMember.findMemberByEmail(email)  // E메일을 이용하여 회원 조회
        if (member == null) {  // E메일로 조회하였을때 회원이 조회되지 않았다면 회원 정보 주입
            member = updateMemberEmail(email)
        }
        val accessToken = jwtTokenService.generateAccessToken(email, member.role)  // Access Token 생성
        val refreshToken = jwtTokenService.generateRefreshToken(email, member.role)  // Refresh Token 생성
        return SigninOrReissueResponse(
            accessToken = accessToken.first,  // accessToken Pair의 첫번째 값
            accessTokenExpiresIn = accessToken.second.toString(),  // accessToken Pair의 두번째 값
            refreshToken = refreshToken.first,  // refreshToken Pair의 첫번째 값
            refreshTokenExpiresIn = refreshToken.second.toString(),  // refreshToken Pair의 두번째 값
            role = member.role  // 회원의 권한 정보
        )
    }

    private val httpTransport: NetHttpTransport = NetHttpTransport()  // HttpTransport(HTTP 통신을 위한 인터페이스) 생성
    private val jsonFactory: GsonFactory = GsonFactory()  // JsonFactory(JSON 데이터를 처리하기 위한 인터페이스) 생성

    private fun authenticateWithCode(code: String): String {  // 코드를 이용하여 인증하는 메서드
        val tokenResponse: GoogleTokenResponse  // GoogleTokenResponse 객체 생성
        try {
            tokenResponse = GoogleAuthorizationCodeTokenRequest(
                httpTransport,
                jsonFactory,
                "https://oauth2.googleapis.com/token",  // Google OAuth2 토큰 요청 URL
                clientId,  // 클라이언트 ID
                clientSecret,  // 클라이언트 Secret
                code,  // 코드
                redirectUri  // 리다이렉트 URI
            ).execute()  // Google OAuth2 토큰 요청 실행
        } catch (e: Exception) {
            throw InvalidCodeException("Invalid Code: ${e.message}")  // 코드가 유효하지 않을 경우 예외 발생
        }
        val idToken = verifyIdToken(tokenResponse.idToken)  // ID 토큰 검증
            ?: throw IllegalArgumentException("Invalid ID Token")
        val payload = idToken.payload
        return payload["email"].toString()  // E메일 반환
    }

    private fun verifyIdToken(idTokenString: String): GoogleIdToken? {  // ID 토큰 검증 메서드
        val verifier = GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)  // GoogleIdTokenVerifier 객체 생성
            .setAudience(listOf(clientId))  // 클라이언트 ID 설정
            .build()
        return verifier.verify(idTokenString)  // ID 토큰 검증 결과 반환
    }

    private fun updateMemberEmail(email: String): Member {  // 회원의 E메일 정보를 업데이트하는 메서드
        val emailHeader = email.split("@")[0]  // E메일 헤더 추출
        if (emailHeader.length != 6 || emailHeader[0] != 's') {  // GSM E메일 형식이 아닐 경우 예외 발생
            throw EmailFormatException("Invalid email format")  // E메일 형식이 잘못되었을 경우 예외 발생
        }
        val admissionYear = emailHeader.substring(1, 3).toInt()  // 입학년도 추출
        val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100  // 현재 년도 추출
        val grade = when (currentYear - admissionYear) {  // 학년 추출
            0 -> 1  // 현재 년도와 입학년도가 같을 경우 1학년
            1 -> 2  // 현재 년도와 입학년도가 1년 차이날 경우 2학년
            2 -> 3  // 현재 년도와 입학년도가 2년 차이날 경우 3학년
            else -> throw EmailFormatException("Not a current student")  // 그 외의 경우 예외 발생
        }
        val studentNumber = emailHeader.substring(3).toInt()  // E메일 뒷 번호 추출
        if (studentNumber !in 1..72) {  // E메일 뒷 번호가 1부터 72까지의 범위가 아닐 경우 예외 발생
            throw EmailFormatException("Invalid student number")  // 학생 번호가 잘못되었을 경우 예외 발생
        }
        val classroom = (studentNumber - 1) / 18 + 1  // 반 추출
        val number = (studentNumber - 1) % 18 + 1  // 번호 추출
        val fucking = fuck(grade, number, classroom)  // 2024학년도 1학년 1반의 번호 밀림 문제를 해결하기 위하여 fuck 함수 호출
        var member: Member? = Member(  // 비어있는 Member 객체 생성
            id = null,
            email = email,
            role = Roles.USER,
            grade = fucking.first,
            classNum = fucking.second,
            number = fucking.third,
            name = "null",
            floor = 2,
            lateNumber = 0,
            room = 301
        )
        if (fucking.first != 0) {  // TODO 2025학년도가 되면 fuck 함수 제거
            member = findMember.findMemberByGradeAndClassNumAndNumber(fucking.first, fucking.second, fucking.third)  // fuck 함수의 반환값이 0이 아니라면 fuck 함수의 반환값을 사용하여 Member 조회
        } else {
            member = findMember.findMemberByGradeAndClassNumAndNumber(grade, classroom, number)  // fuck 함수의 반환값이 0이라면(=2024학년도 1학년 1반의 8번부터 18번까지가 아니라면) 이메일 뒷번호에서 추출한 번호 사용
        }
        member!!.email = email  // null 가능성이 논리적으로 전무하므로 !! 사용
        return saveMember.saveMember(member)  // SaveMember 컴포넌트를 사용하여 member 엔티티 저장
    }

    private fun fuck(grade: Int, number: Int, classNum: Int): Triple<Int, Int, Int> {  // 2024학년도 1학년 1반의 번호 밀림 문제를 보정하는 함수
        val tempStudentId = String.format("%d%d%02d", grade, classNum, number).toInt()  // 학년,반,번호를 학번으로 포맷팅
        var result: Triple<Int, Int, Int> = Triple(0, 0, 0)  // 비어있는 Triple 변수 선언
        if (tempStudentId >= 1109 && tempStudentId <= 1118) {  // 함수가 적용되는 번호대인지 확인
            result = Triple(1, 1, ParseStudentId.parseStudentId(tempStudentId.toString()).third - 1)
        }
        return result
    }
}