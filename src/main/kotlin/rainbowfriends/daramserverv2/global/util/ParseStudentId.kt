package rainbowfriends.daramserverv2.global.util

object ParseStudentId {  // 학번 파싱 객체(싱글톤)
    fun parseStudentId(studentId: String): Triple<Int, Int, Int> {  // 학번을 학년, 반, 번호로 파싱
        return Triple(
            studentId[0].digitToInt(),  // 학년(digitsToInt()로 문자를 숫자로 변환)
            studentId[1].digitToInt(),  // 반(digitsToInt()로 문자를 숫자로 변환)
            studentId.substring(2).toInt()  // 번호(substring()으로 2번째부터 끝까지 자르고, toInt()로 숫자로 변환)
        )
    }
}