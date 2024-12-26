package rainbowfriends.daramserverv2.domain.member.component

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository

@Component
class FindAllMember(private val memberRepository: MemberRepository) {
    @Cacheable(value = ["allMembers"], key = "'allMembers'")  // allMembers라는 이름으로 캐싱
    fun findMemberByCache(): List<Member> {  // 모든 Member 조회
        if (memberRepository.count() <= 0) {  // Member가 없을 경우 예외 발생
            throw MemberNotFoundException("Member Not Found")  // Member Not Found 예외 발생
        }
        return memberRepository.findAll().toList()  // 모든 Member 조회 후 List로 변환
    }
}