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
     * @param parameters    the parameters
     * @param startPosition the start position
     * @param rowsLimit     the rows limit
     * @return the list
     */
    List<GiftCertificate> search(GiftCertificateSearchParams parameters, Integer startPosition, Integer rowsLimit);

    /**
     * Count rows in table by parameters
     *
     * @param searchParams the search params
     * @return the long
     */
    Long countRowsInTable(GiftCertificateSearchParams searchParams);

    /**
     * Is gift certificate used in order boolean.
     *
     * @param id giftCertificate ID
     * @return return true if gift certificates used in orders else return false
     */
    boolean isGiftCertificateUsedInOrder(Long id);
}
