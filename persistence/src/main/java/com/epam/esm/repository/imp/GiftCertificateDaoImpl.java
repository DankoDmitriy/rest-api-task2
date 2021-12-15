package com.epam.esm.repository.imp;

import com.epam.esm.repository.GiftCertificateDao;
import com.epam.esm.repository.GiftCertificateRowMapper;
import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.GiftCertificateSearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private final static int GIFT_CERTIFICATE_NAME_INDEX = 1;
    private final static int GIFT_CERTIFICATE_DESCRIPTION_INDEX = 2;
    private final static int GIFT_CERTIFICATE_PRICE_INDEX = 3;
    private final static int GIFT_CERTIFICATE_DURATION_INDEX = 4;
    private final static int GIFT_CERTIFICATE_CREATE_DATE_INDEX = 5;
    private final static int GIFT_CERTIFICATE_UPDATE_DATE_INDEX = 6;

    private static final String SQL_FIND_ALL_GIFT_CERTIFICATES = "SELECT gift.id as gift_id, gift.name as gift_name,"
            + " gift.description, gift.price, gift.duration, gift.create_date, gift.last_update_date,"
            + " tag.id as tag_id, tag.name as tag_name FROM gift_certificate as gift LEFT JOIN certificate_tag as link"
            + " ON link.gift_certificate_id = gift.id LEFT JOIN tag ON link.tag_id=tag.id ORDER BY gift.id";

    private static final String SQL_FIND_BY_ID_GIFT_CERTIFICATE = "SELECT gift.id as gift_id, gift.name as gift_name,"
            + " gift.description, gift.price, gift.duration, gift.create_date, gift.last_update_date,"
            + " tag.id as tag_id, tag.name as tag_name FROM gift_certificate as gift LEFT JOIN certificate_tag as link "
            + " ON link.gift_certificate_id = gift.id LEFT JOIN tag ON link.tag_id=tag.id where gift.id=?";

    private static final String SQL_SAVE_GIFT_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price,"
            + " duration, create_date, last_update_date) VALUES(?,?,?,?,?,?)";

    private static final String SQL_DELETE_GIFT_CERTIFICATE = "DELETE certificate_tag, gift_certificate"
            + " FROM certificate_tag LEFT JOIN "
            + "gift_certificate ON gift_certificate.id=certificate_tag.gift_certificate_id"
            + " WHERE certificate_tag.gift_certificate_id=?";

    private static final String SQL_DELETE_GIFT_CERTIFICATE_WITH_OUT_TAGS = "DELETE FROM gift_certificate where id=?";

    private static final String SQL_UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate SET name=?, description=?,"
            + " price=?, duration=?, create_date=?, last_update_date=? WHERE id=?";

    private static final String SQL_INSERT_CERTIFICATE_TAG = "INSERT INTO certificate_tag " +
            "(gift_certificate_id, tag_id) VALUES (?, ?);";

    private static final String SQL_DELETE_CERTIFICATE_TAG = "DELETE FROM certificate_tag WHERE gift_certificate_id=? "
            + "AND tag_id=?;";

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

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateRowMapper giftCertificateRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateRowMapper = giftCertificateRowMapper;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_GIFT_CERTIFICATES, giftCertificateRowMapper);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID_GIFT_CERTIFICATE, giftCertificateRowMapper, id)
                .stream().findAny();
    }

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL_SAVE_GIFT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(GIFT_CERTIFICATE_NAME_INDEX, giftCertificate.getName());
            ps.setString(GIFT_CERTIFICATE_DESCRIPTION_INDEX, giftCertificate.getDescription());
            ps.setBigDecimal(GIFT_CERTIFICATE_PRICE_INDEX, giftCertificate.getPrice());
            ps.setInt(GIFT_CERTIFICATE_DURATION_INDEX, giftCertificate.getDuration());
            ps.setTimestamp(GIFT_CERTIFICATE_CREATE_DATE_INDEX, Timestamp.valueOf(giftCertificate.getCreateDate()));
            ps.setTimestamp(GIFT_CERTIFICATE_UPDATE_DATE_INDEX, Timestamp.valueOf(giftCertificate.getLastUpdateDate()));
            return ps;
        }, keyHolder);
        giftCertificate.setId(keyHolder.getKey().longValue());
        return giftCertificate;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_GIFT_CERTIFICATE, id);
        jdbcTemplate.update(SQL_DELETE_GIFT_CERTIFICATE_WITH_OUT_TAGS, id);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        jdbcTemplate.update(SQL_UPDATE_GIFT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(),
                giftCertificate.getId());
        return giftCertificate;
    }

    @Override
    public void attachTag(Long certificateId, Long tagId) {
        jdbcTemplate.update(SQL_INSERT_CERTIFICATE_TAG, certificateId, tagId);
    }

    @Override
    public void detachTag(Long certificateId, Long tagId) {
        jdbcTemplate.update(SQL_DELETE_CERTIFICATE_TAG, certificateId, tagId);
    }

    @Override
    public List<GiftCertificate> search(GiftCertificateSearchParams searchParams) {
        return jdbcTemplate.query(generateSearchSqlCriteria(searchParams), giftCertificateRowMapper);
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