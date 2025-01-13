package org.senla.di.container;

public class BeanFactory {
    private static IocContainer iocContainer;
    private final IocContainer container;

    public BeanFactory() {
        if (iocContainer == null) {
            iocContainer = IocContainer.getInstance();
        }
        this.container = iocContainer;
    }

    public <T> T getBean(Class<T> clazz) {
        Object bean = container.getBean(clazz.getSimpleName());
        if (clazz.isInstance(bean)) {
            return clazz.cast(bean);
        }
        throw new IllegalArgumentException("Bean is not of type " + clazz.getName());
    }

    public Object getBean(String beanName) {
        return container.getBean(beanName);
    }
}
