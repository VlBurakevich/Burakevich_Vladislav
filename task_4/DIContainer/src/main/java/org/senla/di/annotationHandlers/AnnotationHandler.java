package org.senla.di.annotationHandlers;

import java.lang.annotation.Annotation;

public interface AnnotationHandler {
    Class<? extends Annotation> getAnnotationType();
    void handle(Object instance);
    boolean isCritical();
}
