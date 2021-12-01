package com.epam.esm.impl;

import com.epam.esm.TagDao;
import com.epam.esm.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TagServiceImpl implements TagService {
    private TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return tagDao.findById(id);
    }

    @Override
    public Tag save(Tag tag) {
        return tagDao.save(tag);
    }

    @Override
    public void delete(Long id) {
        tagDao.delete(id);
    }
}
