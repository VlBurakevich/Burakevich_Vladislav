package org.senla.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.senla.exceptions.DatabaseDeleteException;
import org.senla.exceptions.DatabaseGetException;
import org.senla.exceptions.DatabaseSaveException;
import org.senla.exceptions.DatabaseUpdateException;
import org.senla.util.EntityManagerUtil;

import java.util.List;

public abstract class GenericRepository<T, K> {

    private final Class<T> entityType;
    protected EntityManager entityManager = EntityManagerUtil.getEntityManager();

    protected GenericRepository(Class<T> entityType) {
        this.entityType = entityType;
    }

    public void save(T entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new DatabaseSaveException(entityType.getSimpleName());
        }
    }

    public T getById(K id) {
        try {
            return entityManager.find(entityType, id);
        } catch (Exception e) {
            throw new DatabaseGetException(entityType.getSimpleName());
        }
    }

    public void deleteById(K id) {
        try {
            entityManager.getTransaction().begin();
            T entity = entityManager.find(entityType, id);
            if (entity != null) {
                entityManager.remove(entity);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new DatabaseDeleteException(entityType.getSimpleName());
        }
    }

    public void update(T entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new DatabaseUpdateException(entityType.getSimpleName());
        }
    }

    public List<T> fetchLimitedRandom(int limit) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityType);
            Root<T> root = cq.from(entityType);

            cq.select(root);

            return entityManager.createQuery(cq)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (Exception e) {
            throw new DatabaseGetException(entityType.getSimpleName());
        }
    }
}
