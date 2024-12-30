package rainbowfriends.daramserverv2.global.member.repository

import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import rainbowfriends.daramserverv2.global.member.entity.Member

@Repository
interface MemberRepository : JpaRepository<Member, Long?> {
    @Cacheable(value = ["MemberByStudentId"], key = "T(String).format('%d%d%02d', #grade, #classNum, #number)")  // 학년, 반, 번호로 조회하는 캐시
    @Query("SELECT m FROM Member m WHERE m.grade = :grade AND m.classNum = :classNum AND m.number = :number")  // 쿼리문
    fun findByGradeAndClassNumAndNumber(grade: Int, classNum: Int, number: Int): Member?
    fun findByEmail(email: String?): Member?
}