package com.epam.esm.hateaos;

import com.epam.esm.contollers.GiftCertificateController;
import com.epam.esm.contollers.OrderController;
import com.epam.esm.contollers.TagController;
import com.epam.esm.contollers.UserController;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HateoasBuilder {
    private final static String DELETE = "delete";
    private static final String UPDATE = "update";

    public void setLinks(TagDto tag) {
        if (tag.getLinks().isEmpty()) {
            tag.add(linkTo(methodOn(TagController.class).getTagById(tag.getId())).withSelfRel());
            tag.add(linkTo(methodOn(TagController.class).deleteTagById(tag.getId())).withRel(DELETE));
        }
    }

    public void setLinks(UserDto user) {
        if (user.getLinks().isEmpty()) {
            user.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
        }
    }

    public void setLinks(OrderDto order) {
        if (order.getLinks().isEmpty()) {
            order.add(linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel());
            order.add(linkTo(methodOn(OrderController.class).deleteOrderById(order.getId())).withRel(DELETE));
        }
        setLinks(order.getUserDto());
        setLinksGiftCertificates(order.getGiftCertificateDtoList());
    }

    public void setLinks(GiftCertificateDto certificate) {
        if (certificate.getLinks().isEmpty()) {
            certificate.add(linkTo(methodOn(GiftCertificateController.class)
                    .getGiftCertificateById(certificate.getId())).withSelfRel());
            certificate.add(linkTo(methodOn(GiftCertificateController.class)
                    .deleteGiftCertificateById(certificate.getId())).withRel(DELETE));
            certificate.add(linkTo(methodOn(GiftCertificateController.class)
                    .updateGiftCertificate(certificate, certificate.getId())).withRel(UPDATE));
        }
        setLinksTags(certificate.getTagDtoList());
    }

    public void setLinksTags(List<TagDto> tags) {
        tags.forEach(tag -> {
            setLinks(tag);
        });
    }

    public void setLinksGiftCertificates(List<GiftCertificateDto> certificates) {
        certificates.forEach(certificate -> {
            setLinks(certificate);
        });
    }

    public void setLinksOrders(List<OrderDto> orders) {
        orders.forEach(order -> {
            setLinks(order);
        });
    }

    public void setLinksUsers(List<UserDto> users) {
        users.forEach(user -> {
            setLinks(user);
        });
    }
}
