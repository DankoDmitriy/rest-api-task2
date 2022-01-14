package com.epam.esm.repository.impl;

import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.GiftCertificateSearchParams;
import com.epam.esm.model.impl.Order;
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
    private static final String ORDER_CERTIFICATES = "certificates";
    private static final String ORDER_CERTIFICATES_ID = "id";
    private static final Integer MINIMUM_SELECT_LIMIT = 1;

    private final EntityManager entityManager;

    @Autowired
    public GiftCertificateDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<GiftCertificate> findAll(Integer startPosition, Integer rowsLimit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> cQuery = cb.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = cQuery.from(GiftCertificate.class);
        return entityManager.createQuery(cQuery).setFirstResult(startPosition).setMaxResults(rowsLimit).getResultList();
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
    public List<GiftCertificate> search(GiftCertificateSearchParams searchParams,
                                        Integer startPosition,
                                        Integer rowsLimit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> cQuery = cb.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = cQuery.from(GiftCertificate.class);

        List<Predicate> searchByParameters = setSearchParameters(searchParams, cb, root);

        cQuery.select(root).where(cb.and(searchByParameters.toArray(new Predicate[searchByParameters.size()])));
        setOrderParameters(cb, cQuery, root, searchParams);

        return entityManager.createQuery(cQuery).setFirstResult(startPosition).setMaxResults(rowsLimit).getResultList();
    }

    @Override
    public boolean isGiftCertificateUsedInOrder(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cQuery = cb.createQuery(Order.class);
        Root<Order> root = cQuery.from(Order.class);
        cQuery.select(root).where(cb.equal(root.join(ORDER_CERTIFICATES).get(ORDER_CERTIFICATES_ID), id));
        List<Order> orders = entityManager.createQuery(cQuery).setMaxResults(MINIMUM_SELECT_LIMIT).getResultList();
        return orders == null ? false : true || orders.isEmpty();
    }

    @Override
    public Long countRowsInTable() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cQuery = cb.createQuery(Long.class);
        cQuery.select(cb.count(cQuery.from(GiftCertificate.class)));
        return entityManager.createQuery(cQuery).getSingleResult();
    }

    @Override
    public Long countRowsInTable(GiftCertificateSearchParams searchParams) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cQuery = cb.createQuery(Long.class);
        Root<GiftCertificate> root = cQuery.from(GiftCertificate.class);

        List<Predicate> searchByParameters = setSearchParameters(searchParams, cb, root);

        cQuery.where(cb.and(searchByParameters.toArray(new Predicate[searchByParameters.size()])));
        cQuery.select(cb.count(root)).where(cb.and(searchByParameters.toArray(new Predicate[searchByParameters.size()])));

        return entityManager.createQuery(cQuery).getSingleResult();
    }

    private List<Predicate> setSearchParameters(GiftCertificateSearchParams searchParams,
                                                CriteriaBuilder cb,
                                                Root<GiftCertificate> root) {

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

        if (searchParams.getTags() != null && !searchParams.getTags().isEmpty()) {
            List<String> tags = searchParams.getTags();
            for (String tag : tags) {
                searchByParameters.add(cb.equal(root.join(GIFT_CERTIFICATE_TAGS).get(TAG_NAME), tag));
            }
        }
        return searchByParameters;
    }

    private void setOrderParameters(CriteriaBuilder cb,
                                    CriteriaQuery<GiftCertificate> cQuery,
                                    Root<GiftCertificate> root,
                                    GiftCertificateSearchParams searchParams) {
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
    }
}
