package com.epam.esm.imp;

import com.epam.esm.TagDao;
import com.epam.esm.impl.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Component
public class TagDaoImpl implements TagDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query("SELECT * FROM Tag", new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag findById(Long id) {
        return jdbcTemplate.query("SELECT * From Tag WHERE id=?", new BeanPropertyRowMapper<>(Tag.class), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public void save(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO tag (name) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);
        tag.setId(keyHolder.getKey().longValue());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM tag WHERE id=?", id);
    }

    @Override
    public void update(Long id, Tag tag) {
        jdbcTemplate.update("UPDATE Tag SET name=? WHERE id=?", tag.getName(), id);
    }
}
