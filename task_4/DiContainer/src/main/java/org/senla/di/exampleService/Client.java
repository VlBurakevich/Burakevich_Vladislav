package org.senla.di.exampleService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.senla.di.annotations.Component;
import org.senla.di.annotations.Value;
import java.math.BigDecimal;


@Component
@Getter
@NoArgsConstructor
public class Client {

    @Value(key = "client.name")
    private String name;

    @Value(key = "client.balance")
    private BigDecimal balance;

    public void addFounds(BigDecimal amount) {
        this.balance = this.balance.add(amount);
        System.out.printf("%s added %.2f to the balance.%n", name, amount);
    }

    public void deductFunds(BigDecimal amount) {
        if (amount.compareTo(balance) <= 0) {
            this.balance = this.balance.subtract(amount);
            System.out.printf("%s spent %.2f. New balance: %.2f%n", name, amount, balance);
        } else {
            System.out.printf("%s doesn't have enough funds.%n", name);
        }
    }
}
