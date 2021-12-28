package com.epam.esm.service;

import com.epam.esm.model.impl.CustomPage;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.service.dto.PageSetup;

/**
 * The interface Tag service.
 */
public interface TagService extends BaseService<Tag> {
    /**
     * Find all custom page.
     *
     * @param pageSetup the page setup
     * @return the custom page
     */
    CustomPage findAll(PageSetup pageSetup);
}
