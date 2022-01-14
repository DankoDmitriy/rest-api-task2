package com.epam.esm.service;

import com.epam.esm.service.dto.CustomPage;
import com.epam.esm.model.impl.Order;
import com.epam.esm.service.dto.PageSetup;

/**
 * The interface Order service.
 */
public interface OrderService extends BaseService<Order> {
    /**
     * Find all custom page.
     *
     * @param pageSetup the page setup
     * @return the custom page
     */
    CustomPage findAll(PageSetup pageSetup);
}
