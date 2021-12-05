package com.epam.esm;

import com.epam.esm.impl.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {
    GiftCertificate update(GiftCertificate giftCertificate);

    void attachTag(Long certificateId, Long tagId);

    void detachTag(Long certificateId, Long tagId);

    List<GiftCertificate> search(Map<String, String[]> parameters);
}
