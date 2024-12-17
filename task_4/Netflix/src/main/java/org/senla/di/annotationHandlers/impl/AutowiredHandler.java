package org.senla.di.annotationHandlers.impl;

import lombok.Setter;
import org.senla.di.annotationHandlers.AnnotationHandler;
import org.senla.di.annotations.Autowired;
import org.senla.di.exceptions.DependencyInjectionException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

@Setter
public class AutowiredHandler implements AnnotationHandler {
    private Map<String, Object> beans;

    @Override
    public Class<? extends Annotation> getAnnotationType() {
        return Autowired.class;
    }

    @Override
    public boolean isCritical() {
        return true;
    }

    @Override
    public void handle(Object instance) {
        for (Field field : instance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                injectDependency(instance, field);
            }
        }
    }

    private void injectDependency(Object instance, Field field) {
        try {
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            String beanName = fieldType.getSimpleName();

            Object bean = beans.get(beanName);

            if (bean == null) {
                bean = findBeanByType(fieldType);
            }

            if (bean != null) {
                field.set(instance, bean);
            } else {
                throw new DependencyInjectionException(
                        DependencyInjectionException.DEPENDENCY_INJECTION_FAILED, instance.getClass().getName());
            }
        } catch (IllegalAccessException e) {
            throw new DependencyInjectionException(
                    DependencyInjectionException.DEPENDENCY_INJECTION_FAILED, instance.getClass().getName());
        } finally {
            field.setAccessible(false);
        }
    }

    private Object findBeanByType(Class<?> fieldType) {
        for (Object obj : beans.values()) {
            if (fieldType.isInstance(obj)) {
                return obj;
            }
        }
        return null;
    }
}
