package com.epam.esm.service;

import com.epam.esm.service.dto.CustomPage;
import com.epam.esm.model.impl.User;
import com.epam.esm.service.dto.PageSetup;

/**
 * The interface User service.
 */
public interface UserService extends BaseService<User> {
    /**
     * Find all custom page.
     *
     * @param pageSetup the page setup
     * @return the custom page
     */
    CustomPage findAll(PageSetup pageSetup);
}
