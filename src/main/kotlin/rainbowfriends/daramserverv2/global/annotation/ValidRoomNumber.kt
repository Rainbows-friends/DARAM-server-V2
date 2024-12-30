package rainbowfriends.daramserverv2.global.annotation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import rainbowfriends.daramserverv2.global.annotation.validator.RoomNumberValidator
import kotlin.reflect.KClass

@MustBeDocumented  // 문서에 표시
@Constraint(validatedBy = [RoomNumberValidator::class])  // 어노테이션을 검증할 클래스
@Target(AnnotationTarget.FIELD)  // 어노테이션을 적용할 위치
@Retention(AnnotationRetention.RUNTIME)  // 어노테이션을 유지할 시점
annotation class ValidRoomNumber(  // 방 번호 유효성 검사(201~213, 301~320, 401~421, 501~518)
    val message: String = "Invalid room number",  // 에러 메시지
    val groups: Array<KClass<*>> = [],  // 그룹(여러개의 어노테이션을 묶어서 사용)
    val payload: Array<KClass<out Payload>> = []  // 페이로드(어노테이션의 부가적인 정보)
)