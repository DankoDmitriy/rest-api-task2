package com.epam.esm.service.impl;

import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.IncorrectEntityException;
import com.epam.esm.exception.UsedEntityException;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.TagDao;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final TagValidator validator;

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
    public Tag findById(Long id) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        if (!optionalTag.isPresent()) {
            throw new EntityNotFoundException(ValidationError.TAG_NOT_FOUND_BY_ID, id);
        }
        return optionalTag.get();
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
        Tag tag = findById(id);
        if (tagDao.isTagUsedInGiftCertificate(id)) {
            throw new UsedEntityException(id);
        }
        tagDao.delete(tag);
    }
}
