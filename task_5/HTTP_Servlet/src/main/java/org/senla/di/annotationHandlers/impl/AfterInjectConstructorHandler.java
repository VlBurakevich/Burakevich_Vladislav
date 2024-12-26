package org.senla.di.annotationHandlers.impl;

import org.senla.di.annotationHandlers.AnnotationHandler;
import org.senla.di.annotations.AfterInjectConstructor;
import org.senla.di.exceptions.DependencyInjectionException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AfterInjectConstructorHandler implements AnnotationHandler {
    private static void invokeMethodWithAnnotation(Object instance, Method method) {
        try {
            method.setAccessible(true);
            method.invoke(instance);
        } catch (Exception e) {
            throw new DependencyInjectionException(
                    DependencyInjectionException.AFTER_INJECT_CONSTRUCTOR_FAILED, method.getName()
            );
        } finally {
            method.setAccessible(false);
        }
    }

    @Override
    public Class<? extends Annotation> getAnnotationType() {
        return AfterInjectConstructor.class;
    }

    @Override
    public boolean isCritical() {
        return false;
    }

    @Override
    public void handle(Object instance) {
        for (Method method : instance.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(AfterInjectConstructor.class)) {
                invokeMethodWithAnnotation(instance, method);
            }
        }
    }
}
