package com.epam.esm.service.impl;

import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.Order;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.model.impl.User;
import com.epam.esm.service.DtoToEntityConverterService;
import com.epam.esm.service.converter.DtoToGiftCertificateConverter;
import com.epam.esm.service.converter.DtoToOrderConverter;
import com.epam.esm.service.converter.DtoToTagConverter;
import com.epam.esm.service.converter.DtoToUserConverter;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DtoToEntityConverterServiceImpl implements DtoToEntityConverterService {
    private final DtoToTagConverter dtoToTagConverter;
    private final DtoToOrderConverter dtoToOrderConverter;
    private final DtoToGiftCertificateConverter dtoToCertificateConverter;
    private final DtoToUserConverter dtoToUserConverter;

    @Autowired
    public DtoToEntityConverterServiceImpl(DtoToTagConverter dtoToTagConverter,
                                           DtoToOrderConverter dtoToOrderConverter,
                                           DtoToGiftCertificateConverter dtoToCertificateConverter,
                                           DtoToUserConverter dtoToUserConverter) {
        this.dtoToTagConverter = dtoToTagConverter;
        this.dtoToOrderConverter = dtoToOrderConverter;
        this.dtoToCertificateConverter = dtoToCertificateConverter;
        this.dtoToUserConverter = dtoToUserConverter;
    }

    @Override
    public Tag convert(TagDto tag) {
        return dtoToTagConverter.convert(tag);
    }

    @Override
    public GiftCertificate convert(GiftCertificateDto certificate) {
        return dtoToCertificateConverter.convert(certificate);
    }

    @Override
    public Order convert(OrderDto order) {
        return dtoToOrderConverter.convert(order);
    }

    @Override
    public User convert(UserDto user) {
        return dtoToUserConverter.convert(user);
    }
}
