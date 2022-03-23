package project.licenta.utils;

import org.apache.deltaspike.cdise.api.CdiContainerLoader;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

public class GetInstance {
    public static Object of(String elName) {
        BeanManager bm = getBeanManager();
        Bean<?> bean = bm.resolve(bm.getBeans(elName));
        return bm.getReference(bean, bean.getBeanClass(), bm.createCreationalContext(bean));
    }

    @SuppressWarnings("unchecked")
    public static <T> T of(Class<T> clazz) {
        BeanManager bm = getBeanManager();
        Bean<?> bean = bm.resolve(bm.getBeans(clazz));
        return (T) bm.getReference(bean, bean.getBeanClass(), bm.createCreationalContext(bean));
    }

    private static BeanManager getBeanManager() {
        try {
            return CdiContainerLoader.getCdiContainer().getBeanManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}