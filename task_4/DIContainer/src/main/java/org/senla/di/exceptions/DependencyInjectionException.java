package org.senla.di.exceptions;

public class DependencyInjectionException extends RuntimeException {
    public static final String COMPONENT_REGISTRATION_FAILED = "Failed to register component: %s";
    public static final String DEPENDENCY_INJECTION_FAILED = "Failed to inject dependency for class: %s";
    public static final String BEAN_NOT_FOUND = "Bean not found: %s";
    public static final String CLASS_NOT_ANNOTATED = "Class %s is not annotated with the required annotation.";
    public static final String BEAN_ALREADY_REGISTERED = "A bean with the name '%s' is already registered as '%s'.";
    public static final String AFTER_INJECT_CONSTRUCTOR_FAILED = "Failed to invoke AfterInjectConstructor %s";
    public DependencyInjectionException(String message, Object... args) {
        super(message.formatted(args));
    }
}
