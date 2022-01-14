package com.epam.esm.repository.impl;

import com.epam.esm.model.impl.Order;
import com.epam.esm.repository.OrderDoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDoa {
    private final EntityManager entityManager;

    @Autowired
    public OrderDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Order> findAll(Integer startPosition, Integer rowsLimit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cQuery = cb.createQuery(Order.class);
        Root<Order> root = cQuery.from(Order.class);
        return entityManager.createQuery(cQuery).setFirstResult(startPosition).setMaxResults(rowsLimit).getResultList();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public Order save(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public void delete(Order order) {
        entityManager.remove(order);
    }

    @Override
    public Long countRowsInTable() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cQuery = cb.createQuery(Long.class);
        cQuery.select(cb.count(cQuery.from(Order.class)));
        return entityManager.createQuery(cQuery).getSingleResult();
    }
}
