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
    private static final String SQL_FIND_ALL_GIFT_CERTIFICATES = "SELECT gift.id as gift_id, gift.name as gift_name, gift.description, gift.price, gift.duration, gift.create_date, gift.last_update_date, tag.id as tag_id, tag.name as tag_name FROM gift_certificate as gift LEFT JOIN certificate_tag as link  ON link.gift_sercificate_id = gift.id LEFT JOIN tag ON link.tag_id=tag.id ORDER BY gift.id";
    private static final String SQL_FIND_BY_ID_GIFT_CERTIFICATE = "SELECT gift.id as gift_id, gift.name as gift_name, gift.description, gift.price, gift.duration, gift.create_date, gift.last_update_date, tag.id as tag_id, tag.name as tag_name FROM gift_certificate as gift LEFT JOIN certificate_tag as link  ON link.gift_sercificate_id = gift.id LEFT JOIN tag ON link.tag_id=tag.id where gift.id=?";
    private static final String SQL_SAVE_GIFT_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES(?,?,?,?,?,?)";
    private static final String SQL_DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=?";
    private static final String SQL_UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate SET name=?, description=?, price=?, duration=?, create_date=?, last_update_date=? WHERE id=?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_GIFT_CERTIFICATES, new GiftCertificateRowMapper());
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID_GIFT_CERTIFICATE, new GiftCertificateRowMapper(), id)
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
}
