package com.epam.esm.service;

import com.epam.esm.model.impl.Order;
import com.epam.esm.service.dto.PageSetup;

import java.util.List;

public interface OrderService extends BaseService<Order> {
    List<Order> findAll(PageSetup pageSetup);
}
