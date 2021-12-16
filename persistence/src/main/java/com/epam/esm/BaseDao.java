package com.epam.esm;

import java.util.List;
import java.util.Optional;

/**
 * The interface Base dao is used for inheritance by interfaces that work with a database.
 * Specifies the minimum required set of methods.
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
     * @param id the id
     */
    void delete(Long id);
}
