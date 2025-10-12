package sample.cafekiosk.spring.api.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ProductController.class) // 컨트롤러 관련 빈들을 간단하게 등록해주는 어노테이션
class ProductControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ProductControllerTest.class);
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // ProductController는 productService가 빈에 등록되어야 사용할 수 있다. 따라서 가짜 객체로 생성하여 사용한다.
    @MockitoBean
    private ProductService productService;

    @DisplayName("신규 상품을 등록한다.")
    @Test
    void createProduct() throws Exception {
        // given
        ProductCreateRequest reqeust = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/products/new")
                            .content(objectMapper.writeValueAsString(reqeust)) // 역직렬화
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(MockMvcResultHandlers.print())// test log 보기 (요청 자세히 볼때 사용)
                    .andExpect(MockMvcResultMatchers.status().isOk()); // 검증, 결과로 ok가 왔는지
    }

    @DisplayName("신규 상품을 등록할 떄 상품 타입은 필수값이다.")
    @Test
    void createProductWithoutType() throws Exception {
        // given
        ProductCreateRequest reqeust = ProductCreateRequest.builder()
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(reqeust)) // 역직렬화
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())// test log 보기 (요청 자세히 볼때 사용)
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // 검증, 결과로 ok가 왔는지
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400)) // 결과에서 나오는 json을 $.{key_name}로 값만 불러와서 맞는지 검증할 수 있다.
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 타입은 필수입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
        ;
    }

    @DisplayName("신규 상품을 등록할 떄 상품 판매상태은 필수값이다.")
    @Test
    void createProductWithoutSellingStatus() throws Exception {
        // given
        ProductCreateRequest reqeust = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .name("아메리카노")
                .price(4000)
                .build();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(reqeust)) // 역직렬화
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())// test log 보기 (요청 자세히 볼때 사용)
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // 검증, 결과로 ok가 왔는지
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400)) // 결과에서 나오는 json을 $.{key_name}로 값만 불러와서 맞는지 검증할 수 있다.
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 판매상태는 필수입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
        ;
    }

    @DisplayName("신규 상품을 등록할 떄 상품 이름은 필수값이다.")
    @Test
    void createProductWithoutName() throws Exception {
        // given
        ProductCreateRequest reqeust = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .price(4000)
                .build();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(reqeust)) // 역직렬화
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())// test log 보기 (요청 자세히 볼때 사용)
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // 검증, 결과로 ok가 왔는지
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400)) // 결과에서 나오는 json을 $.{key_name}로 값만 불러와서 맞는지 검증할 수 있다.
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 이름은 필수입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
        ;
    }

    @DisplayName("신규 상품을 등록할 떄 상품 가격은 양수이다.")
    @Test
    void createProductWithoutZeroPrice() throws Exception {
        // given
        ProductCreateRequest reqeust = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(0)
                .build();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(reqeust)) // 역직렬화
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())// test log 보기 (요청 자세히 볼때 사용)
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // 검증, 결과로 ok가 왔는지
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400)) // 결과에서 나오는 json을 $.{key_name}로 값만 불러와서 맞는지 검증할 수 있다.
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 가격은 양수여야 합니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
        ;
    }

    @DisplayName("판매 상품을 조회한다.")
    @Test
    void getSellingProducts() throws Exception {
        // given
        List<ProductResponse> result = List.of();

        // 가짜 객체가 특정 메소드를 호출받으면 미리 정해놓은 결과를 반환하도록 행동을 지시하는 코드
        Mockito.when(productService.getSellingProducts()).thenReturn(result);
        
        // when
        
        // then
        mockMvc.perform(
                get("/api/v1/products/selling")
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
        ;
    }

}