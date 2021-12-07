package com.epam.esm.impl;

import com.epam.esm.TagDao;
import com.epam.esm.TagService;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.IncorrectEntityException;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TagServiceImpl implements TagService {
    private TagDao tagDao;
    private TagValidator validator;

    @Autowired
    public TagServiceImpl(TagDao tagDao, TagValidator validator) {
        this.tagDao = tagDao;
        this.validator = validator;
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        if (!optionalTag.isPresent()) {
            throw new EntityNotFoundException(ValidationError.TAG_NOT_FOUND_BY_ID, id);
        }
        return tagDao.findById(id);
    }

    @Override
    public Tag save(Tag tag) {
        List<ValidationError> validationErrors = validator.validateTagName(tag.getName());
        if (!validationErrors.isEmpty()) {
            throw new IncorrectEntityException(ValidationError.PROBLEM_WITH_INPUT_PARAMETERS, validationErrors);
        }
        return tagDao.save(tag);
    }

    @Override
    public void delete(Long id) {
        tagDao.delete(id);
    }
}
