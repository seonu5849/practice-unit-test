package sample.cafekiosk.spring.api.controller.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateReqeust;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product/new")
    public void createProduct(ProductCreateReqeust reqeust) {
        productService.createProduct(reqeust);
    }

    @GetMapping("/products/selling")
    public List<ProductResponse> getSellingProducts() {
        return productService.getSellingProducts();
    }



}
