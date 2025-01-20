package org.senla.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.experimental.UtilityClass;
import org.senla.exceptions.DatabaseException;

@UtilityClass
public class EntityManagerUtil {
    private static EntityManagerFactory entityManagerFactory;

    public static EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            try {
                entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
            } catch (Exception e) {
                throw new DatabaseException("EntityManagerUtil exception", e);
            }
        }

        return entityManagerFactory.createEntityManager();
    }
}
