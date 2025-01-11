package org.senla.repository;

import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.senla.di.annotations.Component;
import org.senla.entity.User;
import org.senla.exceptions.DatabaseGetException;

@Component
public class UserRepository extends GenericRepository<User, Long> {

    public UserRepository() {
        super(User.class);
    }

    public User getByUsername(String username) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);

            cq.select(root).where(cb.equal(root.get("username"), username));

            return entityManager.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DatabaseGetException(User.class.getSimpleName());
        }
    }

    public boolean isUsernameExists(String username) {
        return getByUsername(username) != null;
    }
}
