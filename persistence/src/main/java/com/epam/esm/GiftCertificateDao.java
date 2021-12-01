package com.epam.esm;

import com.epam.esm.impl.GiftCertificate;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {
    GiftCertificate update(GiftCertificate giftCertificate);
}
