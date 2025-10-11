package sample.cafekiosk.spring.api.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateReqeust;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products/new")
    public ProductResponse createProduct(@RequestBody ProductCreateReqeust reqeust) {
        return productService.createProduct(reqeust);
    }

    @GetMapping("/products/selling")
    public List<ProductResponse> getSellingProducts() {
        return productService.getSellingProducts();
    }



}
