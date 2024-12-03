package org.senla.di.exampleService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.senla.di.annotations.Component;
import org.senla.di.annotations.Value;

import java.math.BigDecimal;

@Component
@Getter
@NoArgsConstructor
public class Product {

    @Value(key = "product.name")
    private String name;

    @Value(key = "product.price")
    private BigDecimal price;
}
