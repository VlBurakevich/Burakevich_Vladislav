package org.senla.di.exampleService;

import lombok.NoArgsConstructor;
import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;

@Component
@NoArgsConstructor
public class Order {

    @Autowired
    private Client client;

    @Autowired
    private Product product;

    public void placeOrder() {
        if (client.getBalance().compareTo(product.getPrice()) >= 0) {
            client.deductFunds(product.getPrice());
            System.out.printf("Order placed: %s for %.2f%n", product.getName(), product.getPrice());
        } else {
            System.out.printf("Order failed: Not enough funds to buy %s%n", product.getName());
        }
    }

}
