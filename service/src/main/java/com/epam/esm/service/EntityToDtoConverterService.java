package com.epam.esm.service;

import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.Order;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.model.impl.User;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UserDto;

public interface EntityToDtoConverterService {
    TagDto convert(Tag tag);

    GiftCertificateDto convert(GiftCertificate certificate);

    OrderDto convert(Order order);

    UserDto convert(User user);
}
