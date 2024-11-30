package rainbowfriends.daramserverv2.global.member.repository

import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rainbowfriends.daramserverv2.global.member.entity.Member

@Repository
interface MemberRepository : JpaRepository<Member, Long?> {
    @Cacheable(value = ["MemberByStudentId"], key = "#grade + #classNum + #number")
    fun findByGradeAndClassNumAndNumber(grade: Int, classNum: Int, number: Int): Member?
    @Cacheable(value = ["MemberByEmail"], key = "#email")
    fun findByEmail(email: String?): Member?
}