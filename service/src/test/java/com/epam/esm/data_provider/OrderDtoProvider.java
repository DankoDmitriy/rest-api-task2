package com.epam.esm.data_provider;

import com.epam.esm.service.dto.OrderDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class OrderDtoProvider {
    private final UserDtoProvider userProvider = new UserDtoProvider();
    private final GiftCertificateDtoProvider certificateProvider = new GiftCertificateDtoProvider();

    public OrderDto getOrder() {
        OrderDto order = getOrderWithOutId();
        order.setId(1L);
        return order;
    }

    public OrderDto getOrderWithOutId() {
        OrderDto order = new OrderDto();
        order.setCost(certificateProvider.getCorrectGiftCertificate().getPrice());
        order.setPurchaseDate(LocalDateTime.now());
        order.setUserDto(userProvider.getUser());
        order.setGiftCertificateDtoList(certificateProvider.getList());
        return order;
    }

    public List<OrderDto> getOrderList() {
        return Arrays.asList(getOrder());
    }
}
