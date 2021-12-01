package com.epam.esm;

import com.epam.esm.impl.GiftCertificate;

public interface GiftCertificateService extends BaseService<GiftCertificate> {
    GiftCertificate update(GiftCertificate giftCertificate);
}
