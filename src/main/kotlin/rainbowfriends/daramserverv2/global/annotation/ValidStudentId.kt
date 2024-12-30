package rainbowfriends.daramserverv2.global.annotation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import rainbowfriends.daramserverv2.global.annotation.validator.StudentIdValidator
import kotlin.reflect.KClass


@MustBeDocumented  // 문서에 표시
@Constraint(validatedBy = [StudentIdValidator::class])  // 어노테이션을 검증할 클래스
@Target(AnnotationTarget.FIELD)  // 어노테이션을 적용할 위치
@Retention(AnnotationRetention.RUNTIME)  // 어노테이션을 유지할 시점
annotation class ValidStudentId(  // 학번 유효성 검사(1101~1118, 1201~1218, 1301~1318, 1401~1418, 2101~2118, 2201~2218, 2301~2318, 2401~2418, 3101~3118, 3201~3218, 3301~3318, 3401~3418)
    val message: String = "Invalid student ID",  // 에러 메시지
    val groups: Array<KClass<*>> = [],  // 그룹(여러개의 어노테이션을 묶어서 사용)
    val payload: Array<KClass<out Payload>> = []  // 페이로드(어노테이션의 부가적인 정보)
)