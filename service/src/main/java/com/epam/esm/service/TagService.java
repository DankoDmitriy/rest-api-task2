package com.epam.esm.service;

import com.epam.esm.model.impl.Tag;

import java.util.List;

/**
 * The interface Tag service.
 */
public interface TagService extends BaseService<Tag> {
    /**
     * Find list of Tags.
     *
     * @return the list
     */
    List<Tag> findAll();
}
