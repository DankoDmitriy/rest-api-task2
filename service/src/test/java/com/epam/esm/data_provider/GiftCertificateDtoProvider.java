package com.epam.esm.data_provider;

import com.epam.esm.service.dto.GiftCertificateDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiftCertificateDtoProvider {
    private final TagDtoProvider tagProvider = new TagDtoProvider();

    public List<GiftCertificateDto> getEmptyList() {
        return new ArrayList<>();
    }

    public List<GiftCertificateDto> getList() {
        return Arrays.asList(getCorrectGiftCertificate());
    }

    public GiftCertificateDto getCorrectGiftCertificate() {
        GiftCertificateDto certificate = getCorrectGiftCertificateWithOutId();
        certificate.setId(1L);
        return certificate;
    }

    public GiftCertificateDto getCorrectGiftCertificateWithOutId() {
        GiftCertificateDto certificate = new GiftCertificateDto();
        certificate.setName("Gift 1");
        certificate.setDescription("description");
        certificate.setDuration(5);
        certificate.setPrice(BigDecimal.valueOf(100.00));
        certificate.setCreateDate(LocalDateTime.of(2021, 1, 1, 1, 1));
        certificate.setLastUpdateDate(LocalDateTime.of(2021, 1, 1, 1, 1));
        certificate.setTagDtoList(tagProvider.getTagList());
        return certificate;
    }
}
