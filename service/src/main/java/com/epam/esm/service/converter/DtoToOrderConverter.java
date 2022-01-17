package com.epam.esm.service.converter;

import com.epam.esm.model.impl.Order;
import com.epam.esm.service.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DtoToOrderConverter implements Converter<OrderDto, Order> {
    private final DtoToUserConverter dtoToUserConverter;
    private final DtoToGiftCertificateConverter dtoToGiftCertificateConverter;

    @Autowired
    public DtoToOrderConverter(DtoToUserConverter dtoToUserConverter,
                               DtoToGiftCertificateConverter dtoToGiftCertificateConverter) {
        this.dtoToUserConverter = dtoToUserConverter;
        this.dtoToGiftCertificateConverter = dtoToGiftCertificateConverter;
    }

    @Override
    public Order convert(OrderDto source) {
        return Order.builder()
                .id(source.getId())
                .cost(source.getCost())
                .purchaseDate(source.getPurchaseDate())
                .user(dtoToUserConverter.convert(source.getUserDto()))
                .certificates(
                        source.getGiftCertificateDtoList()
                                .stream()
                                .map(dtoToGiftCertificateConverter::convert)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
