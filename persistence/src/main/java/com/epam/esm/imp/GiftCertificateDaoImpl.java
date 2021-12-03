package com.epam.esm.imp;

import com.epam.esm.GiftCertificateDao;
import com.epam.esm.GiftCertificateRowMapper;
import com.epam.esm.impl.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String SQL_FIND_ALL_GIFT_CERTIFICATES = "SELECT gift.id as gift_id, gift.name as gift_name, gift.description, gift.price, gift.duration, gift.create_date, gift.last_update_date, tag.id as tag_id, tag.name as tag_name FROM gift_certificate as gift LEFT JOIN certificate_tag as link  ON link.gift_certificate_id = gift.id LEFT JOIN tag ON link.tag_id=tag.id ORDER BY gift.id";
    private static final String SQL_FIND_BY_ID_GIFT_CERTIFICATE = "SELECT gift.id as gift_id, gift.name as gift_name, gift.description, gift.price, gift.duration, gift.create_date, gift.last_update_date, tag.id as tag_id, tag.name as tag_name FROM gift_certificate as gift LEFT JOIN certificate_tag as link  ON link.gift_certificate_id = gift.id LEFT JOIN tag ON link.tag_id=tag.id where gift.id=?";
    private static final String SQL_SAVE_GIFT_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES(?,?,?,?,?,?)";
    private static final String SQL_DELETE_GIFT_CERTIFICATE = "DELETE certificate_tag, gift_certificate FROM certificate_tag LEFT JOIN gift_certificate ON gift_certificate.id=certificate_tag.gift_certificate_id WHERE certificate_tag.gift_certificate_id=?";
    private static final String SQL_UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate SET name=?, description=?, price=?, duration=?, create_date=?, last_update_date=? WHERE id=?";
    private static final String SQL_INSERT_CERTIFICATE_TAG = "INSERT INTO certificate_tag (gift_certificate_id, tag_id) VALUES (?, ?);";
    private static final String SQL_DELETE_CERTIFICATE_TAG = "DELETE FROM certificate_tag WHERE gift_certificate_id=? AND tag_id=?;";

    private JdbcTemplate jdbcTemplate;
    private GiftCertificateRowMapper giftCertificateRowMapper;

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
            ps.setString(1, giftCertificate.getName());
            ps.setString(2, giftCertificate.getDescription());
            ps.setBigDecimal(3, giftCertificate.getPrice());
            ps.setInt(4, giftCertificate.getDuration());
            ps.setTimestamp(5, Timestamp.valueOf(giftCertificate.getCreateDate()));
            ps.setTimestamp(6, Timestamp.valueOf(giftCertificate.getLastUpdateDate()));
            return ps;
        }, keyHolder);
        giftCertificate.setId(keyHolder.getKey().longValue());
        return giftCertificate;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_GIFT_CERTIFICATE, id);
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
}
