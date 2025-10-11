package sample.cafekiosk.spring.domain.stock;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber;

    private int quantity;

    @Builder
    private Stock(String productNumber, int quantity) {
        this.productNumber = productNumber;
        this.quantity = quantity;
    }

    public static Stock create(String productNumber, int quantity) {
        return Stock.builder()
                .productNumber(productNumber)
                .quantity(quantity)
                .build();
    }

    // 현재 갖고 있는 수량이 요청 들어온 수량보다 작는지 확인, 작다면 true, 크다면 false
    public boolean isQuantityLessThan(int quantity) {
        return this.quantity < quantity;
    }

    public void deductQuantity(int quantity) {
        if(isQuantityLessThan(quantity)) {
            throw new IllegalArgumentException("차감할 재고 수량이 없습니다.");
        }
        this.quantity -= quantity;
    }
}
