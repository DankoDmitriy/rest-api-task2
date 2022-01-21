package com.epam.esm.service;

import com.epam.esm.service.dto.CustomPageDto;
import com.epam.esm.service.dto.OrderDto;
import org.springframework.data.domain.Pageable;

/**
 * The interface Order service.
 */
public interface OrderService extends BaseService<OrderDto> {
    /**
     * Find all orders custom page dto.
     *
     * @param pageable the Pageable
     * @return the custom page dto
     */
    CustomPageDto findAll(Pageable pageable);

    /**
     * Find all orders by user id custom page dto.
     *
     * @param id       the id
     * @param pageable the pageable
     * @return the custom page dto
     */
    CustomPageDto findAllOrdersByUserId(Long id, Pageable pageable);
}
