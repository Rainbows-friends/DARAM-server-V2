package rainbowfriends.daramserverv2.global.member.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository

@Component
class SaveMember(private val memberRepository: MemberRepository) {
    fun saveMember(member: Member): Member {  // 회원 저장
        return memberRepository.save(member)
    }
}