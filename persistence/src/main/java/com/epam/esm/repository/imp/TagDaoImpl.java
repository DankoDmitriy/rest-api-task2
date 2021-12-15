package com.epam.esm.repository.imp;

import com.epam.esm.repository.TagDao;
import com.epam.esm.model.impl.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    private static final int TAG_NAME_INDEX = 1;
    private static final String SQL_FIND_ALL_TAGS = "SELECT * FROM Tag";
    private static final String SQL_FIND_BY_ID_TAG = "SELECT * From Tag WHERE id=?";
    private static final String SQL_SAVE_TAG = "INSERT INTO tag (name) VALUES(?) "
            + " ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id)";

    private static final String SQL_DELETE_TAG = "DELETE certificate_tag, tag "
            + "FROM certificate_tag LEFT JOIN tag ON tag.id=certificate_tag.tag_id "
            + "WHERE certificate_tag.tag_id=?";
    private static final String SQL_DELETE_TAG_WITH_OUT_GIFT_CERTIFICATE = "DELETE FROM tag where id=?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_TAGS, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID_TAG, new BeanPropertyRowMapper<>(Tag.class), id)
                .stream().findAny();
    }

    @Override
    public Tag save(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL_SAVE_TAG, Statement.RETURN_GENERATED_KEYS);
            ps.setString(TAG_NAME_INDEX, tag.getName());
            return ps;
        }, keyHolder);
        tag.setId(keyHolder.getKey().longValue());
        return tag;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_TAG, id);
        jdbcTemplate.update(SQL_DELETE_TAG_WITH_OUT_GIFT_CERTIFICATE, id);
    }
}
