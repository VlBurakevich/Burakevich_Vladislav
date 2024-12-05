package org.senla.di.container;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.senla.di.annotations.AfterInjectConstructor;
import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;
import org.senla.di.exceptions.DependencyInjectionException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class IocContainer {
    private final Map<String, Object> beans = new HashMap<>();

    public IocContainer(String basePackage) {
        Reflections reflections = new Reflections(basePackage, Scanners.TypesAnnotated);

        Set<Class<?>> componentClasses = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> clazz : componentClasses) {
            register(clazz);
        }

        for (Object bean : beans.values()) {
            injectDependencies(bean);
            invokePostConstruct(bean);
        }
    }

    private void invokePostConstruct(Object bean) {
        Class<?> clazz = bean.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AfterInjectConstructor.class)) {
                if (method.getParameterCount() != 0) {
                    throw new DependencyInjectionException(DependencyInjectionException.AFTER_INJECT_CONSTRUCTOR_FAILED, method.getName());
                }
                try {
                    method.setAccessible(true);
                    method.invoke(bean);
                    method.setAccessible(false);
                } catch (Exception e) {
                    throw new DependencyInjectionException(DependencyInjectionException.AFTER_INJECT_CONSTRUCTOR_FAILED, method.getName(), e);
                }
            }
        }
    }

    private void register(Class<?> clazz) {
        String beanName = extractBeanName(clazz);
        validateBeanAbsence(beanName);

        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            beans.put(beanName, instance);
        } catch (ReflectiveOperationException e) {
            throw new DependencyInjectionException(
                    DependencyInjectionException.COMPONENT_REGISTRATION_FAILED, clazz.getName(), e
            );
        }
    }

    private String extractBeanName(Class<?> clazz) {
        Component componentAnnotation = clazz.getAnnotation(Component.class);
        if (componentAnnotation == null) {
            throw new DependencyInjectionException(DependencyInjectionException.CLASS_NOT_ANNOTATED, clazz.getName());
        }

        return componentAnnotation.value().isEmpty() ? clazz.getSimpleName() : componentAnnotation.value();
    }

    private void validateBeanAbsence(String beanName) {
        if (beans.containsKey(beanName)) {
            throw new DependencyInjectionException(
                    DependencyInjectionException.BEAN_ALREADY_REGISTERED, beanName, beans.get(beanName).getClass().getName()
            );
        }
    }

    private void injectDependencies(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                injectDependency(obj, field);
            }
        }
    }

    private void injectDependency(Object obj, Field field) {
        field.setAccessible(true);
        Object dependency = getDependency(field.getType());
        try {
            field.set(obj, dependency);
        } catch (IllegalAccessException e) {
            throw new DependencyInjectionException(DependencyInjectionException.DEPENDENCY_INJECTION_FAILED, obj.getClass().getName(), e);
        }
        field.setAccessible(false);
    }

    private Object getDependency(Class<?> dependencyType) {
        return beans.values().stream()
                .filter(dependencyType::isInstance)
                .findFirst()
                .orElseThrow(() -> new DependencyInjectionException(DependencyInjectionException.BEAN_NOT_FOUND, dependencyType));
    }

    public Object getBean(String name) {
        return Optional.of(name)
                .map(beans::get)
                .orElseThrow(() -> new DependencyInjectionException(DependencyInjectionException.BEAN_NOT_FOUND, name));
    }
}
