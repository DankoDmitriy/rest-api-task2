package com.epam.esm;

import com.epam.esm.impl.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService extends BaseService<GiftCertificate> {
    GiftCertificate update(Long id, GiftCertificate giftCertificate);

    List<GiftCertificate> searchGiftCertificate(Map<String, String[]> searchParams);
}
