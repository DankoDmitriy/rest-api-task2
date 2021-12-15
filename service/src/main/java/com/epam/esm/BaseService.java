package com.epam.esm;

import java.util.Optional;

/**
 * The interface Base service is used for inheritance by interfaces that work with a database.
 * Specifies the minimum required set of methods.
 *
 * @param <E> the type parameter
 */
public interface BaseService<E extends AbstractEntity> {
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
     * @param id the id
     */
    void delete(Long id);
}
