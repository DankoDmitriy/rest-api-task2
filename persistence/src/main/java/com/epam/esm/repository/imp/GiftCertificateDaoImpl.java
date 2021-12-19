package com.epam.esm.repository.imp;

import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.GiftCertificateSearchParams;
import com.epam.esm.repository.GiftCertificateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String GIFT_CERTIFICATE_NAME = "name";
    private static final String GIFT_CERTIFICATE_DESCRIPTION = "description";
    private static final String GIFT_CERTIFICATE_TAGS = "tags";
    private static final String GIFT_CERTIFICATE_CREATE_DATE = "createDate";
    private static final String TAG_NAME = "name";
    private static final String SQL_FIND_ALL_GIFT_CERTIFICATES = "SELECT g FROM GiftCertificate g";

    private final EntityManager entityManager;

    @Autowired
    public GiftCertificateDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return entityManager.createQuery(SQL_FIND_ALL_GIFT_CERTIFICATES).getResultList();
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public void delete(GiftCertificate certificate) {
        entityManager.remove(certificate);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }

    @Override
    public List<GiftCertificate> search(GiftCertificateSearchParams searchParams) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> cQuery = cb.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = cQuery.from(GiftCertificate.class);

        List<Predicate> searchByParameters = new ArrayList<>();

        if (searchParams.getGiftCertificateName() != null && !searchParams.getGiftCertificateName().isEmpty()) {
            searchByParameters.add(cb.like(
                    root.get(GIFT_CERTIFICATE_NAME), "%" + searchParams.getGiftCertificateName() + "%"));
        }
        if (searchParams.getGiftCertificateDescription() != null
                && !searchParams.getGiftCertificateDescription().isEmpty()) {
            searchByParameters.add(cb.like(
                    root.get(GIFT_CERTIFICATE_DESCRIPTION), "%" + searchParams.getGiftCertificateDescription() + "%")
            );
        }
        if (searchParams.getTagName() != null && !searchParams.getTagName().isEmpty()) {
            searchByParameters.add(cb.equal(root.join(GIFT_CERTIFICATE_TAGS).get(TAG_NAME), searchParams.getTagName()));
        }

        cQuery.select(root).where(cb.and(searchByParameters.toArray(new Predicate[searchByParameters.size()])));

        if (searchParams.getSort() != null && searchParams.getSort().size() > 0) {
            Set<String> sort = searchParams.getSort();
            for (String s : sort) {
                switch (s) {
                    case "sortByGiftCertificateName-":
                        cQuery.orderBy(cb.desc(root.get(GIFT_CERTIFICATE_NAME)));
                        break;
                    case "sortByGiftCertificateName+":
                        cQuery.orderBy(cb.asc(root.get(GIFT_CERTIFICATE_NAME)));
                        break;
                    case "sortByCreateDate+":
                        cQuery.orderBy(cb.asc(root.get(GIFT_CERTIFICATE_CREATE_DATE)));
                        break;
                    case "sortByCreateDate-":
                        cQuery.orderBy(cb.desc(root.get(GIFT_CERTIFICATE_CREATE_DATE)));
                        break;
                }
            }
        }
        return entityManager.createQuery(cQuery).getResultList();
    }
}