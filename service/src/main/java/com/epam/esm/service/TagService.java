package com.epam.esm.service;

import com.epam.esm.model.impl.Tag;
import com.epam.esm.service.dto.PageSetup;

import java.util.List;

/**
 * The interface Tag service.
 */
public interface TagService extends BaseService<Tag> {

    /**
     * Find all list.
     *
     * @param pageSetup the page setup
     * @return the list
     */
    List<Tag> findAll(PageSetup pageSetup);
}
