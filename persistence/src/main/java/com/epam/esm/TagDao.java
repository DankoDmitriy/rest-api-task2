package com.epam.esm;

import com.epam.esm.impl.Tag;

import java.util.List;

public interface TagDao {
    public List<Tag> findAll();

    public Tag findById(Long id);

    public void save(Tag tag);

    public void delete(Long id);

    public void update(Long id, Tag tag);
}
