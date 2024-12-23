package org.senla.di.container;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.senla.Main;
import org.senla.di.annotationHandlers.AnnotationHandler;
import org.senla.di.annotationHandlers.impl.AutowiredHandler;
import org.senla.di.annotations.Component;
import org.senla.di.exceptions.DependencyInjectionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

public class IocContainer {
    private static IocContainer instance;

    private final Map<String, Object> beans = new HashMap<>();
    private final List<AnnotationHandler> handlers = new ArrayList<>();

    private IocContainer() {
        String basePackage = Main.class.getPackage().getName();
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

    public static IocContainer getInstance() {
        if (instance == null) {
            instance = new IocContainer();
        }
        return instance;
    }

    private void registerHandlers(Reflections reflections) {
        Set<Class<? extends AnnotationHandler>> handlerClasses = reflections.getSubTypesOf(AnnotationHandler.class);

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
            throw new DependencyInjectionException(DependencyInjectionException.COMPONENT_REGISTRATION_FAILED, clazz.getName());
        }
    }

    private void processHandlers(Object instance, boolean criticalPhase) {
        handlers.stream()
                .filter(handler -> handler.isCritical() == criticalPhase)
                .forEach(handler -> processHandlerForFields(instance, handler));
    }

    private void processHandlerForFields(Object instance, AnnotationHandler handler) {
        Arrays.stream(instance.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(handler.getAnnotationType()))
                .forEach(_ -> handler.handle(instance));
    }

    public Object getBean(String beanName) {
        return Optional.of(beanName)
                .map(beans::get)
                .orElseThrow(() -> new DependencyInjectionException(DependencyInjectionException.BEAN_NOT_FOUND, beanName));
    }
}
