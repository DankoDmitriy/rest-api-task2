package com.epam.esm.repository.impl;

import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.StatisticsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class StatisticsDaoImpl implements StatisticsDao {
    private final static String SQL_FIND_POPULAR_TAG =
            "SELECT t.id, t.name " +
                    "FROM orders o " +
                    "JOIN order_has_certificate co ON o.id = co.order_id " +
                    "JOIN certificate_tag ct ON co.gift_certificate_id = ct.gift_certificate_id " +
                    "JOIN tags t ON t.id = ct.tag_id " +
                    "WHERE o.user_id = (" +
                    "SELECT user_id " +
                    "FROM orders " +
                    "GROUP BY user_id " +
                    "ORDER BY SUM(cost) DESC LIMIT 1) " +
                    "GROUP BY t.id " +
                    "ORDER BY count(t.id) DESC LIMIT 1";

    private final EntityManager entityManager;

    @Autowired
    public StatisticsDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Tag> findPopularTag() {
        Query query = entityManager.createNativeQuery(SQL_FIND_POPULAR_TAG, Tag.class);
        return Optional.ofNullable((Tag) query.getSingleResult());
    }
}
