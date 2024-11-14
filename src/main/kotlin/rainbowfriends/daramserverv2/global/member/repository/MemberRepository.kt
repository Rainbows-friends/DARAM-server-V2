package rainbowfriends.daramserverv2.global.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rainbowfriends.daramserverv2.global.member.entity.Member

@Repository
interface MemberRepository : JpaRepository<Member, Long?>