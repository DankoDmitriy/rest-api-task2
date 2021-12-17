package com.epam.esm.service;

import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.GiftCertificateSearchParams;

import java.util.List;

/**
 * The interface Gift certificate service.
 */
public interface GiftCertificateService extends BaseService<GiftCertificate> {
    /**
     * Update gift certificate.
     *
     * @param id              the id
     * @param giftCertificate the gift certificate
     * @return the gift certificate
     */
    GiftCertificate update(Long id, GiftCertificate giftCertificate);

    /**
     * Find list of gift certificate.
     *
     * @param searchParams the search params
     * @return the list
     */
    List<GiftCertificate> findAll(GiftCertificateSearchParams searchParams);
}