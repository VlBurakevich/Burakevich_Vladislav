package org.senla.di.container;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;
import org.senla.di.exceptiojns.DependencyInjectionException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IoCContainer {
    private final Map<String, Object> beans = new HashMap<>();
    private final ConfigProcessor configProcessor;

    public IoCContainer(String basePackage, Map<String, String> config) {
        this.configProcessor = new ConfigProcessor(config);

        Reflections reflections = new Reflections(basePackage, Scanners.TypesAnnotated);

        Set<Class<?>> componentClasses = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> clazz : componentClasses) {
            try {
                register(clazz);
            } catch (Exception e) {
                throw new DependencyInjectionException(DependencyInjectionException.COMPONENT_REGISTRATION_FAILED, clazz.getName(), e);
            }
        }

        for (Object bean : beans.values()) {
            try {
                injectDependencies(bean);
                configureBean(bean);
            } catch (Exception e) {
                throw new DependencyInjectionException(DependencyInjectionException.DEPENDENCY_INJECTION_FAILED, bean.getClass().getName(), e);
            }
        }
    }

    private void configureBean(Object bean) {
        try {
            configProcessor.process(bean);
        } catch (Exception e) {
            throw new DependencyInjectionException(DependencyInjectionException.DEPENDENCY_INJECTION_FAILED, bean.getClass().getName(), e);
        }
    }

    private void register(Class<?> clazz) {
        try {
            Component componentAnnotation = clazz.getAnnotation(Component.class);
            String beanName = componentAnnotation.value().isEmpty() ? clazz.getSimpleName() : componentAnnotation.value();
            Object instance = clazz.getDeclaredConstructor().newInstance();
            beans.put(beanName, instance);
        } catch (Exception e) {
            throw new DependencyInjectionException(DependencyInjectionException.COMPONENT_REGISTRATION_FAILED, clazz.getName(), e);
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
        Object bean = beans.get(name);
        if (bean == null) {
            throw new DependencyInjectionException(DependencyInjectionException.BEAN_NOT_FOUND, name);
        }
        return bean;
    }
}
