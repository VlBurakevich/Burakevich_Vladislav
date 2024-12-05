package org.senla;

import org.senla.di.container.IocContainer;

public class Main {
    public static void main(String[] args) {
        String basePackage = Main.class.getPackage().getName();
        IocContainer container = new IocContainer(basePackage);
    }
}