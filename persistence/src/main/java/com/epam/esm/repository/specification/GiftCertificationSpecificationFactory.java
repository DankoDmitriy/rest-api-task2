package com.epam.esm.repository.specification;

import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.GiftCertificateSearchParams;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificationSpecificationFactory {
    public Specification<GiftCertificate> createSpecification(GiftCertificateSearchParams searchParams) {
        Specification<GiftCertificate> specification = null;
        if (searchParams.getGiftCertificateDescription() != null) {
            specification = GiftCertificateSpecification.descriptionContains(searchParams.getGiftCertificateDescription());
        }
        if (searchParams.getGiftCertificateName() != null) {
            if (specification != null) {
                specification.and(GiftCertificateSpecification.nameContains(searchParams.getGiftCertificateName()));
            } else {
                specification = GiftCertificateSpecification.nameContains(searchParams.getGiftCertificateName());
            }
        }
        if (searchParams.getTags() != null && !searchParams.getTags().isEmpty()) {
            for (String tag : searchParams.getTags()) {
                if (specification != null) {
                    specification.and(tagSpecification(tag));
                } else {
                    specification = tagSpecification(tag);
                }
            }
        }
        return specification;
    }

    private Specification<GiftCertificate> tagSpecification(String tagName) {
        return GiftCertificateSpecification.tagContains(tagName);
    }
}
