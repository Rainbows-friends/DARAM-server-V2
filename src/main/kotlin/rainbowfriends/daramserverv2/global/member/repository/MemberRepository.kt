package rainbowfriends.daramserverv2.global.member.repository

import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rainbowfriends.daramserverv2.global.member.entity.Member

@Repository
interface MemberRepository : JpaRepository<Member, Long?> {
    @Cacheable(value = ["MemberByStudentId"], key = "#grade + #classNum + #number")  // 학년, 반, 번호로 조회하는 캐시
    fun findByGradeAndClassNumAndNumber(grade: Int, classNum: Int, number: Int): Member?
    @Cacheable(value = ["MemberByEmail"], key = "#email")  // 이메일로 조회하는 캐시
    fun findByEmail(email: String?): Member?
}