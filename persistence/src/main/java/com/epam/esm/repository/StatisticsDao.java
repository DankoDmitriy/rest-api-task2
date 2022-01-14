package com.epam.esm.repository;

import com.epam.esm.model.impl.Tag;

import java.util.Optional;

/**
 * The interface Statistics dao.
 */
public interface StatisticsDao {
    /**
     * Find popular tag optional.
     *
     * @return the optional
     */
    Optional<Tag> findPopularTag();
}
