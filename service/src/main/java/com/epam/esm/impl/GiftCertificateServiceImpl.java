package com.epam.esm.impl;

import com.epam.esm.GiftCertificateDao;
import com.epam.esm.GiftCertificateService;
import com.epam.esm.TagDao;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.IncorrectEntityException;
import com.epam.esm.validator.GiftCertificateSearchParamsValidator;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;
    private GiftCertificateSearchParamsValidator giftCertificateSearchParamsValidator;
    private GiftCertificateValidator giftCertificateValidator;
    private TagValidator tagValidator;

    @Autowired
    public GiftCertificateServiceImpl(
            GiftCertificateDao giftCertificateDao,
            TagDao tagDao,
            GiftCertificateSearchParamsValidator giftCertificateSearchParamsValidator,
            GiftCertificateValidator giftCertificateValidator,
            TagValidator tagValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.giftCertificateSearchParamsValidator = giftCertificateSearchParamsValidator;
        this.giftCertificateValidator = giftCertificateValidator;
        this.tagValidator = tagValidator;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateDao.findAll();
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(id);
        if (!optionalGiftCertificate.isPresent()) {
            throw new EntityNotFoundException(ValidationError.GIFT_CERTIFICATE_NOT_FOUND_BY_ID, id);
        }
        return optionalGiftCertificate;
    }

    @Override
    @Transactional
    public GiftCertificate save(GiftCertificate giftCertificate) {
        List<ValidationError> validationErrors = giftCertificateValidator.validateCertificate(giftCertificate);
        validationErrors.addAll(tagValidator.validateTagNameList(giftCertificate.getTagItems()));

        if (!validationErrors.isEmpty()) {
            throw new IncorrectEntityException(ValidationError.PROBLEM_WITH_INPUT_PARAMETERS, validationErrors);
        }

        LocalDateTime time = LocalDateTime.now();
        giftCertificate.setCreateDate(time);
        giftCertificate.setLastUpdateDate(time);
        giftCertificate = giftCertificateDao.save(giftCertificate);
        if (giftCertificate.getTagItems() != null) {
            attachTagToGiftCertificate(giftCertificate.getId(), giftCertificate.getTagItems());
        }
        return giftCertificate;
    }

    @Override
    public void delete(Long id) {
        giftCertificateDao.delete(id);
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate giftCertificate) {
        List<ValidationError> validationErrors = giftCertificateValidator.validateCertificate(giftCertificate);
        validationErrors.addAll(tagValidator.validateTagNameList(giftCertificate.getTagItems()));
        if (!validationErrors.isEmpty()) {
            throw new IncorrectEntityException(ValidationError.PROBLEM_WITH_INPUT_PARAMETERS, validationErrors);
        }

        GiftCertificate certificateFromDB = giftCertificateDao.findById(giftCertificate.getId()).get();

        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        giftCertificate = giftCertificateDao.update(giftCertificate);
        attachTagToGiftCertificate(giftCertificate.getId(), giftCertificate.getTagItems());

        List<Tag> tagItemsFromDb = certificateFromDB.getTagItems();
        for (Tag tag : tagItemsFromDb) {
            if (!giftCertificate.getTagItems().contains(tag)) {
                giftCertificateDao.detachTag(giftCertificate.getId(), tag.getId());
            }
        }
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> searchGiftCertificate(Map<String, String[]> searchParams) {
        List<ValidationError> validationErrors = giftCertificateSearchParamsValidator.validateSearchParams(searchParams);
        if (!validationErrors.isEmpty()) {
            throw new IncorrectEntityException(ValidationError.PROBLEM_WITH_INPUT_PARAMETERS, validationErrors);
        }
        return giftCertificateDao.search(searchParams);
    }

    private void attachTagToGiftCertificate(Long giftCertificateId, List<Tag> tagItems) {
        for (Tag tag : tagItems) {
            tag = tagDao.save(tag);
            giftCertificateDao.attachTag(giftCertificateId, tag.getId());
        }
    }
}
