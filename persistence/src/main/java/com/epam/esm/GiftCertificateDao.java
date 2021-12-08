package com.epam.esm;

import com.epam.esm.impl.GiftCertificate;
import com.epam.esm.impl.GiftCertificateSearchParams;

import java.util.List;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {
    GiftCertificate update(GiftCertificate giftCertificate);

    void attachTag(Long certificateId, Long tagId);

    void detachTag(Long certificateId, Long tagId);

    List<GiftCertificate> search(GiftCertificateSearchParams parameters);
}
