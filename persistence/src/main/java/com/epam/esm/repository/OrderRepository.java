package com.epam.esm.repository;

import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<GiftCertificate> findFirstByCertificates_id(Long id);

    Page<Order> findAllByUser_Id(Long id, Pageable pageable);
}
