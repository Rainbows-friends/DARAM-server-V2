# DARAM-server-V2

2024 아이디어 페스티벌 AI 기숙사 입사관리 서비스 DARAM의 서버입니다.

---
### Code Convention
+ Component는 Entity와의 소통을 담당하는 단순한 로직만 포함합니다
```kotlin
  @Component
  class CheckInPreparation(private val checkInMongoDBRepository: CheckInMongoDBRepository) {
      @Transactional
      fun prepareCheckIns(checkIns: List<CheckIn>) {
        return checkInMongoRepository.saveAll(
      }
  }
```
+ Service 로직에서는 Entity와 직접적인 트랜잭션을 발행하지 않으며 이를 Component에 위임합니다
+ 일반적인 카멜 케이스 및 Java의 작명 관례를 따릅니다
+ 변수에는 반드시 자료형을 명시합니다
```kotlin
val memberInfo: Member = checkInMemberQuery.getMemberInfo(mongoCheckIn.studentId)
```
+ 가능하다면 키워드 인자를 이용하여 값을 넘기도록 합니다
```kotlin
val allMembers = findAllMember.findMemberByCache().map {
            GetMemberResponse(
                id = it.id,
                stay = it.stay,
                floor = it.floor,
                room = it.room,
                grade = it.grade,
                classNum = it.classNum,
                name = it.name,
                role = it.role,
                lateNumber = it.lateNumber,
                number = it.number
            )
```
+ enum 클래스는 dto 패키지 내부에 enums 패키지를 생성하여 추가하도록 하며 DTO는 Request DTO와 Response DTO로 분리하여 사용합니다
+ DDD(도메인 주도 설계)를 기본 소프트웨어 아키택쳐로 하며 프로젝트 전역적으로 사용되는 클래스는 global 패키지로, 특정 도메인 객체에서 한정적으로 사용되는 클래스는 domain 패키지내 알맞은 패키지에서 사용합니다
+ 사용자 지정 예외는 반드시 global.exception.handler 내부의 클래스에서 처리로직을 작성하도록 하고 클라이언트에 알맞은 HTTP 응답을 반환하도록 합니다
+ 더 이상 사용되지 않는 클래스는 Deprecated 처리하도록 합니다
```kotlin
@Deprecated("Use JwtTokenService instead")
data class TokenResponse(
    val accessToken: String
)
```


### Commit Message Rule
Keyword | Description
--|-- 
feat | 새로운 기능이나 코드가 추가되었을 때
fix | 오류를 수정했을 때
docs | 문서를 수정했을 때
style | 단순한 코드 형식 변경, 코드 변경이 없는 경우
refactor | 코드 리팩토링을 했을 때
test | 테스트 코드를 작성했을 때
chore | 빌드 스크립트,종속성 등을 수정했을 때
ci | CI/CD 파이프라인에 변화가 있을 때
init | 프로젝트를 새로 시작했을 때
pull | 단순히 브랜치 간의 병합으로 인한 커밋일 경우
merge | PR을 머지할 때

Footer | Description
--|--
Fixes | issue가 해결되었을 때 사용
Resolves | issue를 해결하고 있을 때 사용
Ref | 참고할 issue가 있을 때 사용
Related to | 해당 커밋에 관련된 issue 번호에 사용

```
feat: 회원 가입 기능 구현

회원 가입 기능을 구현하였습니다

Resolves: #123
Ref: #456
Related to: #48, #45
```
