package rainbowfriends.daramserverv2.domain.time.service

import rainbowfriends.daramserverv2.domain.time.dto.enums.GetRemainTimeServiceAction

interface RemainTimeService {
    fun getRemainTime(getRemainTimeServiceAction: GetRemainTimeServiceAction): Any
}