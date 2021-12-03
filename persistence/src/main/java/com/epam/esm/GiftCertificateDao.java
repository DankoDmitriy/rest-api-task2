package com.epam.esm;

import com.epam.esm.impl.GiftCertificate;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {
    GiftCertificate update(GiftCertificate giftCertificate);

    void attachTag(Long certificateId, Long tagId);

    void detachTag(Long certificateId, Long tagId);
}
