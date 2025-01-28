package org.senla.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.senla.exceptions.DatabaseDeleteException;
import org.senla.exceptions.DatabaseGetException;
import org.senla.exceptions.DatabaseSaveException;
import org.senla.exceptions.DatabaseUpdateException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
public abstract class GenericRepository<T, K> {

    private final Class<T> entityType;
    @PersistenceContext
    protected EntityManager entityManager;

    protected GenericRepository(Class<T> entityType) {
        this.entityType = entityType;
    }

    @Transactional
    public void save(T entity) {
        log.info("Executing save operation for entity: {}", entityType.getSimpleName());
        try {
            entityManager.persist(entity);
        } catch (Exception e) {
            throw new DatabaseSaveException(entityType.getSimpleName());
        }
    }

    public T getById(K id) {
        log.info("Executing getById operation for entity: {}, ID: {}", entityType.getSimpleName(), id);
        try {
            return entityManager.find(entityType, id);
        } catch (Exception e) {
            throw new DatabaseGetException(entityType.getSimpleName());
        }
    }

    @Transactional
    public void deleteById(K id) {
        log.info("Executing deleteById operation for entity: {}, ID: {}", entityType.getSimpleName(), id);
        try {
            T entity = entityManager.find(entityType, id);
            if (entity != null) {
                entityManager.remove(entity);
            }
        } catch (Exception e) {
            throw new DatabaseDeleteException(entityType.getSimpleName());
        }
    }

    @Transactional
    public void update(T entity) {
        log.info("Executing update operation for entity: {}", entityType.getSimpleName());
        try {
            entityManager.merge(entity);
        } catch (Exception e) {
            throw new DatabaseUpdateException(entityType.getSimpleName());
        }
    }

    public List<T> fetchLimitedRandom(int limit) {
        log.info("Executing fetchLimitedRandom operation for entity: {}, Limit: {}", entityType.getSimpleName(), limit);
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
