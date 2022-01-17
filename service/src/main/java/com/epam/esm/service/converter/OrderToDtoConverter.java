package com.epam.esm.service.converter;

import com.epam.esm.model.impl.Order;
import com.epam.esm.service.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderToDtoConverter implements Converter<Order, OrderDto> {
    private final UserToDtoConverter userToDtoConverter;
    private final GiftCertificateToDtoConverter giftCertificateToDtoConverter;

    @Autowired
    public OrderToDtoConverter(UserToDtoConverter userToDtoConverter,
                               GiftCertificateToDtoConverter giftCertificateToDtoConverter) {
        this.userToDtoConverter = userToDtoConverter;
        this.giftCertificateToDtoConverter = giftCertificateToDtoConverter;
    }

    @Override
    public OrderDto convert(Order source) {
        return OrderDto.builder()
                .id(source.getId())
                .cost(source.getCost())
                .purchaseDate(source.getPurchaseDate())
                .userDto(userToDtoConverter.convert(source.getUser()))
                .giftCertificateDtoList(
                        source.getCertificates()
                                .stream()
                                .map(giftCertificateToDtoConverter::convert)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
