package rainbowfriends.daramserverv2.domain.member.service

import rainbowfriends.daramserverv2.domain.member.dto.request.PatchRoomRequest

interface PatchRoomService {
    fun patchRoom(request: PatchRoomRequest)
}