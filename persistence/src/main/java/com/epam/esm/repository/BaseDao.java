package com.epam.esm.repository;

import com.epam.esm.model.AbstractEntity;

import java.util.List;
import java.util.Optional;

/**
 * The interface Base dao is used for inheritance by interfaces that work with a database.
 * Specifies the minimum required to be set of methods.
 *
 * @param <E> the type parameter
 */
public interface BaseDao<E extends AbstractEntity> {
    /**
     * Find all Entities.
     *
     * @return the list
     */
    List<E> findAll();

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
     * @param <E> the e
     */
    void delete(E e);
}
