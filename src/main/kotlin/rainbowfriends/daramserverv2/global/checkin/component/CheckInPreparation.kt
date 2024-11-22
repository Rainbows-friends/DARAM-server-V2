package rainbowfriends.daramserverv2.global.checkin.component

import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInMongoDBRepository
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository
import java.time.LocalDate

@Component
class CheckInPreparation(
    private val checkInRepository: CheckInRepository,
    private val memberRepository: MemberRepository,
    private val checkInMongoDBRepository: CheckInMongoDBRepository,
    private val checkInDataSync: CheckInDataSync
) {
    @Transactional
    fun prepareCheckInsForDate(date: LocalDate) {
        val allMember: List<Member> = memberRepository.findAll()
        if (checkInMongoDBRepository.findByCheckinDate(date).isEmpty()) {
            val checkIns = allMember.map { member ->
                CheckIn(
                    id = null,
                    user = member,
                    checkinDate = date,
                    checkinStatus = false
                )
            }
            checkInRepository.saveAll(checkIns)
            syncCheckInsToMongoDBAsync(checkIns)
        }
    }

    @Async
    fun syncCheckInsToMongoDBAsync(checkIns: List<CheckIn>) {
        checkIns.forEach { checkIn ->
            checkInDataSync.syncCheckInToMongoDB(checkIn)
        }
    }
}