package com.epam.esm;

import com.epam.esm.impl.GiftCertificate;
import com.epam.esm.impl.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
    private static final String GIFT_ID = "gift_id";
    private static final String GIFT_NAME = "gift_name";
    private static final String GIFT_DESCRIPTION = "description";
    private static final String GIFT_PRICE = "price";
    private static final String GIFT_DURATION = "duration";
    private static final String GIFT_CREATE_DATE = "create_date";
    private static final String GIFT_LAST_UPDATE_DATE = "last_update_date";
    private static final String TAG_ID = "tag_id";
    private static final String TAG_NAME = "tag_name";

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
        List<Tag> tags = new ArrayList<>();

        certificate.setTagItems(tags);
        certificate.setId(rs.getLong(GIFT_ID));
        certificate.setName(rs.getString(GIFT_NAME));
        certificate.setDescription(rs.getString(GIFT_DESCRIPTION));
        certificate.setPrice(rs.getBigDecimal(GIFT_PRICE));
        certificate.setDuration(rs.getInt(GIFT_DURATION));
        certificate.setCreateDate(rs.getTimestamp(GIFT_CREATE_DATE).toLocalDateTime());
        certificate.setLastUpdateDate(rs.getTimestamp(GIFT_LAST_UPDATE_DATE).toLocalDateTime());

        resultSetStepper(rs, tags, certificate.getId());
        return certificate;
    }

    private void resultSetStepper(ResultSet rs, List<Tag> tags, Long thisCertificateId) throws SQLException {
        tagRowMapper(rs, tags);
        while (rs.next()) {
            if (thisCertificateId.equals(rs.getLong(GIFT_ID))) {
                tagRowMapper(rs, tags);
            } else {
                rs.previous();
                break;
            }
        }
    }

    private void tagRowMapper(ResultSet rs, List<Tag> tags) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getLong(TAG_ID));
        tag.setName(rs.getString(TAG_NAME));
        tags.add(tag);
    }
}
