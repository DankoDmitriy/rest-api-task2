package com.epam.esm.service;

import com.epam.esm.model.impl.GiftCertificateSearchParams;
import com.epam.esm.service.dto.CustomPageDto;
import com.epam.esm.service.dto.GiftCertificateDto;
import org.springframework.data.domain.Pageable;

/**
 * The interface Gift certificate service.
 */
public interface GiftCertificateService extends BaseService<GiftCertificateDto> {
    /**
     * Update gift certificate.
     *
     * @param id              the id
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     */
    GiftCertificateDto update(Long id, GiftCertificateDto giftCertificateDto);

    /**
     * Find all custom page.
     *
     * @param searchParams the search params
     * @param pageable    the Pageable
     * @return the custom page
     */
    CustomPageDto findAll(GiftCertificateSearchParams searchParams, Pageable pageable);
}
