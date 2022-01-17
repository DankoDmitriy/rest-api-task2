package com.epam.esm.service;

import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.Order;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.model.impl.User;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UserDto;

public interface DtoToEntityConverterService {
    Tag convert(TagDto tag);

    GiftCertificate convert(GiftCertificateDto certificate);

    Order convert(OrderDto order);

    User convert(UserDto user);
}
