package com.epam.esm;

import com.epam.esm.impl.Tag;

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
