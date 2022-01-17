package com.epam.esm.repository;

import com.epam.esm.model.impl.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
