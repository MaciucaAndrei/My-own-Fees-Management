package project.licenta.producer;

import org.apache.deltaspike.jpa.api.entitymanager.PersistenceUnitName;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@ApplicationScoped
public class EntityManagerProducer {
    @Inject
    @PersistenceUnitName("sample-unit")
    private EntityManagerFactory entityManagerFactory;

    @Produces
    @Default
    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    /**
     * Closes the entity manager produced earlier. It's called automatically by CDI container
     *
     * @param entityManager the entity manager produced earlier in the {@link #getEntityManager()} method
     */
    public void closeEntityManager(@Disposes @Default EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

    /**
     * Closes the entity manager factory instance so that the CDI container can be gracefully shutdown
     */
    @PreDestroy
    public void closeFactory() {
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}