package com.epam.esm.service;

import com.epam.esm.service.dto.CustomPage;
import com.epam.esm.model.impl.Order;
import com.epam.esm.service.dto.CustomPageDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.PageSetup;
import org.springframework.data.domain.Pageable;

/**
 * The interface Order service.
 */
public interface OrderService extends BaseService<OrderDto> {
    /**
     * Find all custom page.
     *
     * @param pageable the Pageable
     * @return the custom page dto
     */
    CustomPageDto findAll(Pageable pageable);
}
