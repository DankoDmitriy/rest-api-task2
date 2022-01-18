package com.epam.esm.repository.specification;

import com.epam.esm.model.impl.GiftCertificate;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class GiftCertificateSpecification {
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String GIFT_CERTIFICATE_TAGS = "tags";
    private static final String TAG_NAME = "name";

    public static Specification<GiftCertificate> nameContains(String name) {
        return new Specification<GiftCertificate>() {
            @Override
            public Predicate toPredicate(Root<GiftCertificate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get(NAME), "%" + name + "%");
            }
        };
    }

    public static Specification<GiftCertificate> descriptionContains(String desc) {
        return new Specification<GiftCertificate>() {
            @Override
            public Predicate toPredicate(Root<GiftCertificate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get(DESCRIPTION), "%" + desc + "%");
            }
        };
    }

    public static Specification<GiftCertificate> tagContains(String tagName) {
        return new Specification<GiftCertificate>() {
            @Override
            public Predicate toPredicate(Root<GiftCertificate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.join(GIFT_CERTIFICATE_TAGS).get(TAG_NAME), tagName);
            }
        };
    }
}
