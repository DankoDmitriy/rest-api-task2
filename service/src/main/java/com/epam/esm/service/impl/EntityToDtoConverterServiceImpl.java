package com.epam.esm.service.impl;

import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.Order;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.model.impl.User;
import com.epam.esm.service.EntityToDtoConverterService;
import com.epam.esm.service.converter.GiftCertificateToDtoConverter;
import com.epam.esm.service.converter.OrderToDtoConverter;
import com.epam.esm.service.converter.TagToDtoConverter;
import com.epam.esm.service.converter.UserToDtoConverter;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityToDtoConverterServiceImpl implements EntityToDtoConverterService {
    private final TagToDtoConverter tagToDtoConverter;
    private final GiftCertificateToDtoConverter certificateToDtoConverter;
    private final OrderToDtoConverter orderToDtoConverter;
    private final UserToDtoConverter userToDtoConverter;

    @Autowired
    public EntityToDtoConverterServiceImpl(TagToDtoConverter tagToDtoConverter,
                                           GiftCertificateToDtoConverter certificateToDtoConverter,
                                           OrderToDtoConverter orderToDtoConverter,
                                           UserToDtoConverter userToDtoConverter) {
        this.tagToDtoConverter = tagToDtoConverter;
        this.certificateToDtoConverter = certificateToDtoConverter;
        this.orderToDtoConverter = orderToDtoConverter;
        this.userToDtoConverter = userToDtoConverter;
    }

    @Override
    public TagDto convert(Tag tag) {
        return tagToDtoConverter.convert(tag);
    }

    @Override
    public GiftCertificateDto convert(GiftCertificate certificate) {
        return certificateToDtoConverter.convert(certificate);
    }

    @Override
    public OrderDto convert(Order order) {
        return orderToDtoConverter.convert(order);
    }

    @Override
    public UserDto convert(User user) {
        return userToDtoConverter.convert(user);
    }
}
