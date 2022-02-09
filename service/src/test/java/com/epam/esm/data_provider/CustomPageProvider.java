package com.epam.esm.data_provider;

import com.epam.esm.service.dto.CustomPage;
import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.Order;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.model.impl.User;

public class CustomPageProvider {
    private final GiftCertificateProvider certificateProvider = new GiftCertificateProvider();
    private final OrderProvider orderProvider = new OrderProvider();
    private final TagProvider tagProvider = new TagProvider();
    private final UserProvider userProvider = new UserProvider();

    public CustomPage<GiftCertificate> getCustomPageGiftCertificate() {
        CustomPage<GiftCertificate> customPage = new CustomPage<>();
        customPage.setItems(certificateProvider.getList());
        return customPage;
    }

    public CustomPage<Order> getCustomPageOrder() {
        CustomPage<Order> customPage = new CustomPage<>();
        customPage.setItems(orderProvider.getOrderList());
        return customPage;
    }

    public CustomPage<Tag> getCustomPageTag() {
        CustomPage<Tag> customPage = new CustomPage<>();
        customPage.setItems(tagProvider.getTagList());
        return customPage;
    }

    public CustomPage<User> getCustomPageUser() {
        CustomPage<User> customPage = new CustomPage<>();
        customPage.setItems(userProvider.getUserList());
        return customPage;
    }
}