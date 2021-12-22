package com.epam.esm.repository;

import com.epam.esm.model.AbstractEntity;

import java.util.List;
import java.util.Optional;

/**
 * The interface Base dao.
 *
 * @param <E> the type parameter
 */
public interface BaseDao<E extends AbstractEntity> {
    /**
     * Find all list.
     *
     * @param startPosition the startPosition number
     * @param rowsLimit     the max result on the startPosition
     * @return the list
     */
    List<E> findAll(Integer startPosition, Integer rowsLimit);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<E> findById(Long id);

    /**
     * Save e.
     *
     * @param e the e
     * @return the e
     */
    E save(E e);

    /**
     * Delete.
     *
     * @param e the e
     */
    void delete(E e);

    /**
     * Rows in table long.
     *
     * @return the long
     */
    Long rowsInTable();
}
