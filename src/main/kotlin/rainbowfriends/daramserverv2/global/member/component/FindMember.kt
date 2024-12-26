package rainbowfriends.daramserverv2.global.member.component

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository

@Component
@Transactional(readOnly = true)
class FindMember(private val memberRepository: MemberRepository) {
    fun findMemberByEmail(email: String): Member? {  // 이메일로 회원 조회
        return memberRepository.findByEmail(email)
    }

    fun findMemberByGradeAndClassNumAndNumber(grade: Int, classNum: Int, number: Int): Member? {   // 학년, 반, 번호로 회원 조회
        return memberRepository.findByGradeAndClassNumAndNumber(grade, classNum, number)
    }
}