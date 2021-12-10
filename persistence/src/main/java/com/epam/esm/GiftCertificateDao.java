package com.epam.esm;

import com.epam.esm.impl.GiftCertificate;
import com.epam.esm.impl.GiftCertificateSearchParams;

import java.util.List;

/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDao extends BaseDao<GiftCertificate> {
    /**
     * Update gift certificate.
     *
     * @param giftCertificate the gift certificate
     * @return the gift certificate
     */
    GiftCertificate update(GiftCertificate giftCertificate);

    /**
     * Attach tag.
     *
     * @param certificateId the certificate id
     * @param tagId         the tag id
     */
    void attachTag(Long certificateId, Long tagId);

    /**
     * Detach tag.
     *
     * @param certificateId the certificate id
     * @param tagId         the tag id
     */
    void detachTag(Long certificateId, Long tagId);

    /**
     * Search Entities.
     *
     * @param parameters the parameters
     * @return the list
     */
    List<GiftCertificate> search(GiftCertificateSearchParams parameters);
}
