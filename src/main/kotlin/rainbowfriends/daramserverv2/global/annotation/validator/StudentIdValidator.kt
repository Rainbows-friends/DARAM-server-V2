package rainbowfriends.daramserverv2.global.annotation.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import rainbowfriends.daramserverv2.global.annotation.ValidStudentId

class StudentIdValidator : ConstraintValidator<ValidStudentId, Short> {  // 학번 유효성 검사(1101~1118, 1201~1218, 1301~1318, 1401~1418, 2101~2118, 2201~2218, 2301~2318, 2401~2418, 3101~3118, 3201~3218, 3301~3318, 3401~3418),어노테이션에 적용
    override fun isValid(value: Short?, context: ConstraintValidatorContext): Boolean {
        if (value == null) return false  // null이면 false
        val allowedRanges = listOf(
            1101..1118, 1201..1218, 1301..1318, 1401..1418,
            2101..2118, 2201..2218, 2301..2318, 2401..2418,
            3101..3118, 3201..3218, 3301..3318, 3401..3418
        )  // 학번이 1101~1118, 1201~1218, 1301~1318, 1401~1418, 2101~2118, 2201~2218, 2301~2318, 2401~2418, 3101~3118, 3201~3218, 3301~3318, 3401~3418이면 true
        return allowedRanges.any { range -> value in range }  // 학번이 허용된 범위에 있으면 true
    }
}