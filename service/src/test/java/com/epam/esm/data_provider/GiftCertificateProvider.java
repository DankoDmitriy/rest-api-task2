package com.epam.esm.data_provider;

import com.epam.esm.model.impl.GiftCertificate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GiftCertificateProvider {

    private final TagProvider tagProvider = new TagProvider();

    public List<GiftCertificate> getEmptyList() {
        return new ArrayList<>();
    }

    public List<GiftCertificate> getList() {
        return Collections.singletonList(getCorrectGiftCertificate());
    }

    public GiftCertificate getCorrectGiftCertificate() {
        GiftCertificate certificate = getCorrectGiftCertificateWithOutId();
        certificate.setId(1L);
        return certificate;
    }

    public GiftCertificate getCorrectGiftCertificateWithOutId() {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setName("Gift 1");
        certificate.setDescription("description");
        certificate.setDuration(5);
        certificate.setPrice(BigDecimal.valueOf(100.00));
        certificate.setCreateDate(LocalDateTime.of(2021, 1, 1, 1, 1));
        certificate.setLastUpdateDate(LocalDateTime.of(2021, 1, 1, 1, 1));
        certificate.setTags(tagProvider.getTagList());
        return certificate;
    }

}
