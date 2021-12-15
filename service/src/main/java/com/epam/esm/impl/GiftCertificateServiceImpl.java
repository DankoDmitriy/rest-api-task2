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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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
    public List<GiftCertificate> findAll(GiftCertificateSearchParams searchParams) {
        List<ValidationError> validationErrors = giftCertificateSearchParamsValidator.validateSearchParams(searchParams);
        if (validationErrors.contains(ValidationError.FIND_ALL)) {
            return giftCertificateDao.findAll();
        } else {
            if (!validationErrors.isEmpty()) {
                throw new IncorrectEntityException(ValidationError.PROBLEM_WITH_INPUT_PARAMETERS, validationErrors);
            }
            return giftCertificateDao.search(searchParams);
        }
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
        validationErrors.addAll(tagValidator.validateTagNameList(giftCertificate.getTags()));

        if (!validationErrors.isEmpty()) {
            throw new IncorrectEntityException(ValidationError.PROBLEM_WITH_INPUT_PARAMETERS, validationErrors);
        }

        LocalDateTime time = LocalDateTime.now();
        giftCertificate.setCreateDate(time);
        giftCertificate.setLastUpdateDate(time);
        giftCertificate = giftCertificateDao.save(giftCertificate);
        if (giftCertificate.getTags() != null) {
            attachTagToGiftCertificate(giftCertificate.getId(), giftCertificate.getTags());
        }
        return giftCertificate;
    }

    @Override
    public void delete(Long id) {
        giftCertificateDao.delete(id);
    }

    @Override
    @Transactional
    public GiftCertificate update(Long id, GiftCertificate giftCertificate) {
        List<ValidationError> validationErrors = new ArrayList<>();
        GiftCertificate certificateFromDB = findById(id).get();
        giftCertificate.setCreateDate(certificateFromDB.getCreateDate());

        giftCertificate.setId(certificateFromDB.getId());
        checkGiftCertificateBeforeUpdate(certificateFromDB, giftCertificate, validationErrors);

        if (!validationErrors.isEmpty()) {
            throw new IncorrectEntityException(ValidationError.PROBLEM_WITH_INPUT_PARAMETERS, validationErrors);
        }

        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        giftCertificate = giftCertificateDao.update(giftCertificate);

        if (giftCertificate.getTags() != null && giftCertificate.getTags().size() == 0) {
            for (Tag tagItem : certificateFromDB.getTags()) {
                giftCertificateDao.detachTag(id, tagItem.getId());
            }
        } else {
            if (giftCertificate.getTags() != null) {
                List<Tag> oldTagsItems = certificateFromDB.getTags();
                for (Tag tag : oldTagsItems) {
                    giftCertificateDao.detachTag(id, tag.getId());
                }
                List<Tag> newTagsItems = giftCertificate.getTags();
                attachTagToGiftCertificate(id, newTagsItems);
            }
        }
        return giftCertificate;
    }

    private void attachTagToGiftCertificate(Long giftCertificateId, List<Tag> tagItems) {
        for (Tag tag : tagItems) {
            tag = tagDao.save(tag);
            giftCertificateDao.attachTag(giftCertificateId, tag.getId());
        }
    }

    private void checkGiftCertificateBeforeUpdate(
            GiftCertificate certificateFromDB,
            GiftCertificate giftCertificate,
            List<ValidationError> validationErrors) {
        if (giftCertificate.getName() != null) {
            giftCertificateValidator.validateName(giftCertificate.getName(), validationErrors);
        } else {
            giftCertificate.setName(certificateFromDB.getName());
        }

        if (giftCertificate.getDescription() != null) {
            giftCertificateValidator.validateDescription(giftCertificate.getDescription(), validationErrors);
        } else {
            giftCertificate.setDescription(certificateFromDB.getDescription());
        }

        if (giftCertificate.getDuration() != null) {
            giftCertificateValidator.validateDuration(giftCertificate.getDuration(), validationErrors);
        } else {
            giftCertificate.setDuration(certificateFromDB.getDuration());
        }

        if (giftCertificate.getPrice() != null) {
            giftCertificateValidator.validatePrice(giftCertificate.getPrice(), validationErrors);
        } else {
            giftCertificate.setPrice(certificateFromDB.getPrice());
        }
        validationErrors.addAll(tagValidator.validateTagNameList(giftCertificate.getTags()));
    }
}
