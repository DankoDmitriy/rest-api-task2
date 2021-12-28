package com.epam.esm.service;

import com.epam.esm.model.impl.CustomPage;
import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.GiftCertificateSearchParams;
import com.epam.esm.service.dto.PageSetup;

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
     * Find all custom page.
     *
     * @param searchParams the search params
     * @param pageSetup    the page setup
     * @return the custom page
     */
    CustomPage findAll(GiftCertificateSearchParams searchParams, PageSetup pageSetup);
}
