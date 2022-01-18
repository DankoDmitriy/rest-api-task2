package com.epam.esm.data_provider;

import com.epam.esm.model.impl.Order;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class OrderProvider {
    private final UserProvider userProvider = new UserProvider();
    private final GiftCertificateProvider certificateProvider = new GiftCertificateProvider();

    public Order getOrder() {
        Order order = getOrderWithOutId();
        order.setId(1L);
        return order;
    }

    public Order getOrderWithOutId() {
        Order order = new Order();
        order.setCost(certificateProvider.getCorrectGiftCertificate().getPrice());
        order.setPurchaseDate(LocalDateTime.now());
        order.setUser(userProvider.getUser());
        order.setCertificates(certificateProvider.getList());
        return order;
    }

    public List<Order> getOrderList() {
        return Arrays.asList(getOrder());
    }
}
