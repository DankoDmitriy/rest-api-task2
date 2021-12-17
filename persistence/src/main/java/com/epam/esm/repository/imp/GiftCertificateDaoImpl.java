package com.epam.esm.repository.imp;

import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.GiftCertificateSearchParams;
import com.epam.esm.repository.GiftCertificateDao;
import com.epam.esm.repository.GiftCertificateRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String SQL_FIND_ALL_GIFT_CERTIFICATES = "FROM GiftCertificate";

    private static final String SQL_FIND_GIFT_CERTIFICATE = "SELECT gift.id as gift_id, gift.name as gift_name,"
            + " gift.description, gift.price, gift.duration, gift.create_date, gift.last_update_date,"
            + " tag.id as tag_id, tag.name as tag_name FROM gift_certificate as gift LEFT JOIN certificate_tag as link "
            + " ON link.gift_certificate_id = gift.id LEFT JOIN tag ON link.tag_id=tag.id ";

    private static final String SEARCH_BY_END = "%' ";
    private final static String SEARCH_BY_TAG_NAME = " tag.name LIKE '%";
    private final static String SEARCH_BY_GIFT_CERTIFICATE_NAME = " gift.name LIKE '%";
    private final static String SEARCH_BY_GIFT_CERTIFICATE_DESCRIPTION = " description LIKE '%";

    private final static String ORDER_BY_GIFT_CERTIFICATE_NAME_DESC = " gift.name DESC ";
    private final static String ORDER_BY_GIFT_CERTIFICATE_NAME_ASC = " gift.name ASC ";
    private final static String ORDER_BY_GIFT_CERTIFICATE_CREATE_DATE_DESC = " gift.create_date DESC ";
    private final static String ORDER_BY_GIFT_CERTIFICATE_CREATE_DATE_ASC = " gift.create_date ASC ";
    private final static String ORDER_BY_GIFT_CERTIFICATE_ID = " gift.id ASC ";

    private static final String SQl_COMMA = ", ";
    private static final String SQL_AND = " and ";
    private static final String SQl_ORDER = " ORDER BY ";
    private static final String SQL_WHERE = " WHERE ";

    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateRowMapper giftCertificateRowMapper;
    private final EntityManager entityManager;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate,
                                  GiftCertificateRowMapper giftCertificateRowMapper,
                                  EntityManager entityManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateRowMapper = giftCertificateRowMapper;
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
//TODO
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> cQuery = cb.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = cQuery.from(GiftCertificate.class);

        ParameterExpression<String> paramGift = cb.parameter(String.class);

        String giftCertificateName = searchParams.getGiftCertificateName();
        String giftCertificateDescription = searchParams.getGiftCertificateDescription();


        List<Predicate> searchParam = new ArrayList<>();
        searchParam.add(cb.like(root.get("name"), "%" + giftCertificateName + "%"));
        cQuery.select(root).where(cb.and(searchParam.toArray(new Predicate[searchParam.size()])));
        return entityManager.createQuery(cQuery).getResultList();
    }

    private String generateSearchSqlCriteria(GiftCertificateSearchParams searchParams) {
        StringBuilder sb = new StringBuilder();
        sb.append(SQL_FIND_GIFT_CERTIFICATE);
        boolean putSqlWhere = false;
        if (searchParams.getTagName() != null) {
            sb.append(SQL_WHERE).append(SEARCH_BY_TAG_NAME).append(searchParams.getTagName()).append(SEARCH_BY_END);
            putSqlWhere = true;
        }
        if (searchParams.getGiftCertificateName() != null) {
            if (putSqlWhere) {
                sb.append(SQL_AND)
                        .append(SEARCH_BY_GIFT_CERTIFICATE_NAME)
                        .append(searchParams.getGiftCertificateName())
                        .append(SEARCH_BY_END);
            } else {
                sb.append(SQL_WHERE)
                        .append(SEARCH_BY_GIFT_CERTIFICATE_NAME)
                        .append(searchParams.getGiftCertificateName())
                        .append(SEARCH_BY_END);
                putSqlWhere = true;
            }
        }
        if (searchParams.getGiftCertificateDescription() != null) {
            if (putSqlWhere) {
                sb.append(SQL_AND)
                        .append(SEARCH_BY_GIFT_CERTIFICATE_DESCRIPTION)
                        .append(searchParams.getGiftCertificateDescription())
                        .append(SEARCH_BY_END);
            } else {
                sb.append(SQL_WHERE)
                        .append(SEARCH_BY_GIFT_CERTIFICATE_DESCRIPTION)
                        .append(searchParams.getGiftCertificateDescription())
                        .append(SEARCH_BY_END);
            }
        }

        sb.append(SQl_ORDER);
        if (searchParams.getSort() != null && searchParams.getSort().size() > 0) {
            Set<String> sort = searchParams.getSort();
            for (String s : sort) {
                switch (s) {
                    case "giftCertificateName-":
                        sb.append(ORDER_BY_GIFT_CERTIFICATE_NAME_DESC)
                                .append(SQl_COMMA);
                        break;
                    case "giftCertificateName+":
                        sb.append(ORDER_BY_GIFT_CERTIFICATE_NAME_ASC)
                                .append(SQl_COMMA);
                        break;
                    case "sortByCreateDate+":
                        sb.append(ORDER_BY_GIFT_CERTIFICATE_CREATE_DATE_ASC)
                                .append(SQl_COMMA);
                        break;
                    case "sortByCreateDate-":
                        sb.append(ORDER_BY_GIFT_CERTIFICATE_CREATE_DATE_DESC)
                                .append(SQl_COMMA);
                        break;
                }
            }
        }
        sb.append(ORDER_BY_GIFT_CERTIFICATE_ID);
        return sb.toString();
    }
}