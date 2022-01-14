package com.epam.esm.service;

import com.epam.esm.model.AbstractEntity;

/**
 * The interface Base service is used for inheritance by interfaces that work with a database.
 * Specifies the minimum required to be set of methods.
 *
 * @param <E> the type parameter
 */
public interface BaseService<E extends AbstractEntity> {
    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the E
     */
    E findById(Long id);

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
     * @param id the id
     */
    void delete(Long id);
}
