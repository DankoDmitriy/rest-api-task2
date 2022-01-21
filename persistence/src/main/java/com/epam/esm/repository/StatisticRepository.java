package com.epam.esm.repository;

import com.epam.esm.model.impl.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StatisticRepository extends JpaRepository<Tag, Long> {
    public final static String SQL_FIND_POPULAR_TAG =
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

    @Query(value = SQL_FIND_POPULAR_TAG, nativeQuery = true)
    Optional<Tag> findPopularTag();
}
