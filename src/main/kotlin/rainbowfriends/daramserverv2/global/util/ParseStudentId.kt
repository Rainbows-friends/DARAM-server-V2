package rainbowfriends.daramserverv2.global.util

object ParseStudentId {
    fun parseStudentId(studentId: String): Triple<Int, Int, Int> {
        return Triple(
            studentId[0].digitToInt(),
            studentId[1].digitToInt(),
            studentId.substring(2).toInt()
        )
    }
}