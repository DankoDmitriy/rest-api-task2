package com.epam.esm.repository.impl;

import com.epam.esm.model.impl.User;
import com.epam.esm.repository.UserDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    private final EntityManager entityManager;

    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAll(Integer startPosition, Integer rowsLimit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cQuery = cb.createQuery(User.class);
        Root<User> root = cQuery.from(User.class);
        return entityManager.createQuery(cQuery).setFirstResult(startPosition).setMaxResults(rowsLimit).getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public Long countRowsInTable() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cQuery = cb.createQuery(Long.class);
        cQuery.select(cb.count(cQuery.from(User.class)));
        return entityManager.createQuery(cQuery).getSingleResult();
    }
}
