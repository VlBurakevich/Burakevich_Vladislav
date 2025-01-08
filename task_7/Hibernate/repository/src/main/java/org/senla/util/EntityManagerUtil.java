package org.senla.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.senla.exceptions.DatabaseException;

public class EntityManagerUtil {
    private static EntityManagerFactory entityManagerFactory;

    private EntityManagerUtil() {}

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
