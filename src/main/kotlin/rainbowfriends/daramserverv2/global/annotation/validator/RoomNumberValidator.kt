package rainbowfriends.daramserverv2.global.annotation.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import rainbowfriends.daramserverv2.global.annotation.ValidRoomNumber

class RoomNumberValidator : ConstraintValidator<ValidRoomNumber, Short> {  // 방 번호 유효성 검사(201~213, 301~320, 401~421, 501~518),어노테이션에 적용
    override fun isValid(value: Short?, context: ConstraintValidatorContext): Boolean {
        if (value == null) return false  // null이면 false
        return value in 201..213 || value in 301..320 || value in 401..421 || value in 501..518  // 방 번호가 201~213, 301~320, 401~421, 501~518이면 true
    }
}