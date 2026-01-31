# practice-unit-test
학습 인강: [Practical Testing: 실용적인 테스트 가이드](https://www.inflearn.com/course/practical-testing-%EC%8B%A4%EC%9A%A9%EC%A0%81%EC%9D%B8-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EA%B0%80%EC%9D%B4%EB%93%9C/dashboard)


## 테스트를 하는 이유
- 자동화 테스트로 비교적 빠른 시간 안에 버그를 발견할 수 있고, 수동 테스트에 드는 비용을 크게 절약할 수 있다.
- 소프트웨어의 빠른 변화를 지원한다.
- 팀원들의 집단 지성을 팀 차원의 이익으로 승격시킨다.
- 가까이 보면 느리지만, 멀리보면 가장 빠르다. (테스트 코드를 작성하는 중에는 오래걸리지만, 추후 테스트를 돌려 검증하여 오류를 발견하여 최소화할 수 있다.)
---
## TDD, BDD
- TDD(Test Driven Development)
  - 실제 구현 코드보다 테스트 코드를 먼저 작성하여 테스트가 구현 과정을 주도하도록 하는 방법론
  - RED, GREEN, REFACTOR 단계로 진행
    - RED: 실패하는 테스트 작성
    - GREEN: 테스트 통과를 위한 최소한의 코딩
    - REFACTOR: 구현 코드 새선, 테스트 통과 유지
  - TDD를 사용하는 이유
    - 복잡도가 낮은(유지보수 쉬운), 테스트 가능한 코드로 구현할 수 있다.
    - 쉽게 발견하기 어려운 엣지 케이스를 놓치지 않게 해준다.
    - 구현에 대한 빠른 피드백을 받을 수 있다.
    - 과감한 리펙토링이 가능해진다.


- BDD(Behavior Driven Development)
  - TDD에서 파생된 개발 방법
  - 함수 단위의 테스트에 집중하기보다, **시나리오**에 기반한 테스트케이스 자체에 집중하여 테스트
  - 개발자가 아닌 사람이 봐도 이해할 수 있을 정도의 추상화 수준을 권장
  - 주로 Given / When / Then 단계로 구분하여 테스트 코드 구현
    - Given: 시나리오 진행에 필요한 모든 준비 과정 (객체, 값, 상태 등)
    - When: 시나리오 행동 진행
    - Then: 시나리오 진행에 대한 결과 명시, 검증
---

## Layered Architecture 테스트
### Presentation Layer
- 외부 세계의 요청을 가장 먼저 받는 계층
- 파라미터에 대한 최소한의 검증을 수행
- 테스트 방법
  - Business Layer, Persistence Layer를 Mocking
  - Presentation Layer를 TDD 방식으로 구현

### Business Layer
- 애플리케이션의 핵심 비즈니스 로직을 처리하는 계층
- 트랜잭션을 관리하고, Presentation Layer와 Persistence Layer를 중개하는 역할
- 테스트 방법
  - Persistence Layer를 Mocking (테스트 방법에 따라 적용 선택)
  - TDD 방식으로 테스트를 먼저 구현하면서 비즈니스 로직을 구현하고 개선해나감.
  - 서비스 로직의 수행 결과를 검증
### Persistence Layer
- 데이터베이스와의 직접적인 데이터 송수신을 담당하는 계층
- 테스트 방법
  - @DataJpaTest 어노테이션을 통해 가볍게 테스트를 진행할 수 있음.
  - DB에 대한 결과값을 검증

---
## 테스트 도구
### JUnit 5 프레임워크
- 테스트의 전체구조와 실행을 담당
- 주로 어노테이션 형태로 사용


- 클래스 단위 어노테이션
    ```java
    @SpringBootTest : 
    - 스프링 부트 기반으로 Build를 수행하여 테스트를 진행
    - 모든 Bean을 가져옴
    - 전체 애플리케이션을 테스트할 때 사용할 수 있다.
    - @Transactional이 없어서 변경감지, Rollback등이 나타나지 않는다.
            
    @DataJpaTest : 
    - Jpa 게층을 테스트할때 사용. @SpringBootTest보다 가벼움
    - 즉, JPA관련 Bean만을 가져오고, Repository 관련 테스트를 할 때 사용
    ```

- 메소드 단위 어노테이션
    ```java
    @Test : 테스트 메소드임을 정의
    @DisplayName : 테스트의 실행 이름을 지정
    @Order : 테스트 실행 순서 조정
    @AfterEach, @BeforeEach: 테스트 시작 전후로 각 테스트가 실행할 때 실행됨. (테스트 메소드 개수만틈 실행)
    @AfterAll, @BeforeAll: 전체테스트가 실행 전후로 실행됨. (한번만 실행)
    ```
### AssertJ 라이브러리
- 테스트 코드 안에서 값을 검증하기 위해 호출하는 도구 모음
- **메소드 체이닝**을 사용하여 연속된 검증을 할 수 있음
    ```java
    import static org.assertj.core.api.Assertions.assertThat;
    ```
- 주요 메소드
    ```java
    assertThat(actual).isEqualsTo(expected); // 실제값과 기대값이 같은지 검증
    assertThat(actual).isSize(expected); // 길이를 비교
    assertThat(actual).isTrue(); // boolean 타입이 true인지 확인, 반대는 isFalse()가 있음
    assertThat(actual).isEmpty(); // 빈값인지 확인, 반대는 isNotEmpty()
    assertThat(object).extracting("id", "name", "price"); // 객체의 필드를 꺼내서 검증, 주로 contains 관련 메소드와 함께 사용
    assertThat(object).extracting("id", "name", "price").contains("001", "아메리카노", 4000); // 객체에서 추출한 필드대로 contains에 작성한 기대값과 동일한지 확인
    assertThat(object).extracting("id", "name", "age").containsExactlyInAnyOrder(
            tuple("001", "아메리카노", 4000),
            tuple("001", "카페라떼", 4500)
    ); // 객체가 리스트, 배열 형태일때, 순서와 상관없이(AnyOrder) 들어있는 값만 검증할 때 사용, 만약 순서도 중요하다면 containsExactly() 메소드를 사
    ```

### MockMvc 프레임워크
- Mock(가짜) 객체를 사용해 스프링 MVC 동작을 재현할 수 있는 테스트 프레임워크
- 클래스 단위 어노테이션
    ```java
    @WebMvcTest(ProductController.class): 해당 컨트롤러에서 사용한 Bean을 간단하게 등록해주는 어노테이션
    @ExtendWith(MockitoExtension.class): 순수 Mock 테스트가 시작될 때, Mockito를 이용해서 mock을 만들거라는 것을 인지시키기 위해 사용 (@Mock, @Spy 어노테이션이 작동하도록 해줌)
    ```

- 필드 단위 어노테이션
    ```java
    @MockitoBean: 가짜 객체로 생성할 클래스명을 지정,
    @Spy: (@SpringBootTest x), 순수 Mock일때, 실제 객체를 기반으로 특정 메서드만 가짜로 바꿀 수 있는 객체를 생성
    @SpyBean: (@SpringBootTest o), 동작은 @Spy와 동일
    @Mock: (@SpringBootTest x), 순수 Mock 클래스를 만들기 위한 어노테이션,
    @InjectMocks: 생성된 @Mock이나 @Spy 객체들을 테스트 대상 객체에 자동으로 주입
    ```
- Mockito 객체
    ```java
    MockMvc: mockito 프레임워크를 사용가능하게하는 구현 클래스
    ```

- 주요 메소드
    ```java
    // 가짜 객체가 특정 메소드를 호출받으면 미리 정해놓은 결과를 반환하도록 행동을 지시하는 코드
    Mockito.when(productService.getSellingProducts()).thenReturn(result);
  
    mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/v1/products/new") // GET, POST와 같은 HTTP Method를 정의
                    .content(objectMapper.writeValueAsString(reqeust)) // API의 request를 정의 (GET은 사용하지 않음)
                    .contentType(MediaType.APPLICATION_JSON) // request의 contentType을 정의 (GET은 사용하지 않음)
            )
            .andDo(MockMvcResultHandlers.print())// test log 보기 (요청 자세히 볼때 사용)
            .andExpect(MockMvcResultMatchers.status().isBadRequest()) // 검증, 결과로 ok가 왔는지
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400)) // 결과에서 나오는 json을 $.{key_name}로 값만 불러와서 맞는지 검증할 수 있다.
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 타입은 필수입니다."))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
    ;
    ```
  
- Mockito의 when 메소드를 통해 Mock 객체에서 원하는 응답값을 반환
```java
// any(String.class)는 String 타입이면 어떤것이든 상관없다 라는 뜻
// 즉, String 타입으로 매개변수가 4개 들어왔을 때 무조건 true를 응답해라 라는 의미이다.
Mockito.when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
        .thenReturn(true);
```

- @Spy 어노테이션으로 일부 메소드만 가짜 객체로 변환
```java
Mockito.doReturn(true) // 응답값 세팅
        .when(mailSendClient) // 가짜 메소드로 변환할 클래스 지정
        .sendEmail(anyString(), anyString(), anyString(), anyString()); // 가짜 객체 중에서 가짜 메소드로 변환하려는 메소드
```

- BDDMocito.given()
```java
// Mockito.when과 BDDMockito.given은 동일한 동작을 수행하지만, BDDMockito의 given은 BDD의 의미를 부여한 것이다. (그냥 이름만 바뀜)
Mockito.when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString())).thenReturn(true);
BDDMockito.given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString())).willReturn(true);
```

### Test Double 용어 정리
- Dummy: 아무것도 하지 않는 깡통 객체
- Fake: 단순한 형태로 동일한 기능을 수행하지만, 프로덕션에서 쓰기는 부족한 객체
    (ex. FakeRepository: In-Memory로 구현한 간단한 저장소)
- stub: 테스트에서 요청한 것에 대해 미리 준비한 결과를 제공하는 객체. 그 외에는 응답하지 않음
  (즉, 해당 요청값에는 정해진 응답값만을 제공)
- spy: stub이면서 호출된 내용을 기록히여 보여줄 수 있는 객체. 일부는 실제 객체처럼 동작시키고 일부만 stubbing 할 수 있다.
- mock: 행위에 대한 기대를 명세하고, 그에 따라 동작하도록 만들어진 객체

#### Stub과 Mock의 차이
- 둘은 가짜 객체이고, 요청한 결과에 대해 정해진 결과를 반환한다.
- 하지만 stub은 상태를 검증하고, mock은 행위를 검증한다.
- stub -> 어떤 메소드, 어떤 기능에 요청을 했을 때 내부 로직이 동작하게 되고 그로 인해 상태가 변경된 것을 검증 (메시지가 전송되었을 때 몇개의 메시지가 전송되었는지를 확인)
- mock -> 어떤 메소드, 어떤 기능에 요청을 했을 때 어떤 값을 반환할지에 대한 행위를 검증