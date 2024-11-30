package rainbowfriends.daramserverv2.domain.auth.service

import rainbowfriends.daramserverv2.domain.auth.dto.response.SigninOrReissueResponse

interface ReissueService {
    fun reissue(refreshToken: String): SigninOrReissueResponse
}