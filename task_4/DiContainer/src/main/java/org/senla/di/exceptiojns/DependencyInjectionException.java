package org.senla.di.exceptiojns;

public class DependencyInjectionException extends RuntimeException {
    public static final String COMPONENT_REGISTRATION_FAILED = "Failed to register component: %s";
    public static final String DEPENDENCY_INJECTION_FAILED = "Failed to inject dependency for class: %s";
    public static final String BEAN_NOT_FOUND = "Bean not found: %s";

    public DependencyInjectionException(String message, Object... args) {
        super(String.format(message, args));
    }
}
