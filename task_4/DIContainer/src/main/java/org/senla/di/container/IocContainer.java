package org.senla.di.container;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.senla.di.annotationHandlers.AnnotationHandler;
import org.senla.di.annotations.Component;
import org.senla.di.exceptions.DependencyInjectionException;
import org.senla.di.annotationHandlers.impl.AutowiredHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class IocContainer {
    private final Map<String, Object> beans = new HashMap<>();
    private final List<AnnotationHandler> handlers = new ArrayList<>();

    public IocContainer(String basePackage) {
        Reflections reflections = new Reflections(basePackage, Scanners.TypesAnnotated, Scanners.SubTypes);

        registerHandlers(reflections);

        Set<Class<?>> componentClasses = reflections.getTypesAnnotatedWith(Component.class);

        for (Class<?> clazz : componentClasses) {
            registerComponent(clazz);
        }

        for (Object bean : beans.values()) {
            processHandlers(bean, true);
        }

        for (Object bean : beans.values()) {
            processHandlers(bean, false);
        }
    }

    private void registerHandlers(Reflections reflections) {
        Set<Class<? extends AnnotationHandler>> handlerClasses =
                reflections.getSubTypesOf(AnnotationHandler.class);

        for (Class<? extends AnnotationHandler> handlerClass : handlerClasses) {
            try {
                AnnotationHandler handler = handlerClass.getDeclaredConstructor().newInstance();
                if (handler instanceof AutowiredHandler autowiredHandler) {
                    autowiredHandler.setBeans(beans);
                }
                handlers.add(handler);
            } catch (Exception e) {
                throw new DependencyInjectionException(
                        DependencyInjectionException.HANDLER_REGISTRATION_FAILED, handlerClass.getName()
                );
            }
        }
    }

    private void registerComponent(Class<?> clazz) {
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            beans.put(clazz.getSimpleName(), instance);
        } catch (Exception e) {
            throw new DependencyInjectionException(DependencyInjectionException.HANDLER_REGISTRATION_FAILED, clazz.getName());
        }
    }

    private void processHandlers(Object instance, boolean criticalPhase) {
        handlers.stream()
                .filter(handler -> handler.isCritical() == criticalPhase)
                .filter(handler -> instance.getClass().isAnnotationPresent(handler.getAnnotationType()))
                .forEach(handler -> handler.handle(instance));
    }

    public Object getBean(String beanName) {
        return Optional.of(beanName)
                .map(beans::get)
                .orElseThrow(() -> new DependencyInjectionException(DependencyInjectionException.BEAN_NOT_FOUND, beanName));
    }
}
