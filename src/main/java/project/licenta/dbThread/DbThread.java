package project.licenta.dbThread;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import project.licenta.entity.User;
import project.licenta.service.UserService;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import java.util.List;
import java.util.Set;

public class DbThread implements Runnable {

    private CdiContainer cdiContainer;
    @Override
    public void run() {

        initCdiContainer();

        try {
            persistPersons();
            fetchAllPersons();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            shutdownCdiContainer();
        }
    }
    private void fetchAllPersons() {
        UserService userService = (UserService) getBean(UserService.class);

        List<User> all = userService.findAll();

        System.out.println(all);
    }


    private void persistPersons() {
        UserService userService = (UserService) getBean(UserService.class);

        userService.save("John", "Smith");
        userService.save("Will", "Smith");
        userService.save("Black", "Smith");

    }

    private void initCdiContainer() {
        cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();
    }

    private void shutdownCdiContainer() {
        cdiContainer.shutdown();
    }

    /**
     * Encapsulates the necessary steps to get a reference to an instance of a Bean via CDI container
     *
     * @param type Type of the bean requested
     * @return An instance of the requested bean (either newly created or an already existing instance based on the
     * scope of the bean)
     */
    private Object getBean(Class type) {
        BeanManager beanManager = cdiContainer.getBeanManager();
        Set<Bean<?>> personServiceBean = beanManager.getBeans(type);
        Bean<?> bean = beanManager.resolve(personServiceBean);
        CreationalContext<?> context = beanManager.createCreationalContext(bean);
        return beanManager.getReference(bean, type, context);
    }

}
