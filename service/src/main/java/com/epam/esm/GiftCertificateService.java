package com.epam.esm;

import com.epam.esm.impl.GiftCertificate;
import com.epam.esm.impl.GiftCertificateSearchParams;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificate> {
    GiftCertificate update(Long id, GiftCertificate giftCertificate);

    List<GiftCertificate> findAll(GiftCertificateSearchParams searchParams);
}
