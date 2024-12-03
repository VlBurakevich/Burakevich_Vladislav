package org.senla;

import org.senla.di.container.IoCContainer;

public class Main {
    public static void main(String[] args) {
        String basePackage = Main.class.getPackage().getName();
        IoCContainer container = new IoCContainer(basePackage);
    }
}