package org.senla;

import org.senla.di.container.ConfigLoader;
import org.senla.di.container.IoCContainer;
import org.senla.di.exampleService.Order;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            ConfigLoader configLoader = new ConfigLoader("application.properties");
            Map<String, String> config = configLoader.getConfig();

            String basePackage = Main.class.getPackage().getName();
            IoCContainer container = new IoCContainer(basePackage, config);

            Order order = (Order) container.getBean("Order");

            order.placeOrder();
        } catch (Exception e) {
            System.err.printf("Error: %s - %s%n", e.getClass().getSimpleName(), e.getMessage());
        }
    }
}