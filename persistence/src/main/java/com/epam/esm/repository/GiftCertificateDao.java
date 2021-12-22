package com.epam.esm.repository;

import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.GiftCertificateSearchParams;

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
     * Search list.
     *
     * @param parameters the parameters
     * @param page       the page
     * @param pageSize   the page size
     * @return the list
     */
    List<GiftCertificate> search(GiftCertificateSearchParams parameters, Integer startPosition, Integer rowsLimit);

    Long rowsInTable(GiftCertificateSearchParams searchParams);

    boolean isGiftCertificateUsedInOrder(Long id);
}
