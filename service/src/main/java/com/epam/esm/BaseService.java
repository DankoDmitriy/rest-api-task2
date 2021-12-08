package com.epam.esm;

import java.util.Optional;

public interface BaseService<E extends AbstractEntity> {
    Optional<E> findById(Long id);

    E save(E e);

    void delete(Long id);
}
