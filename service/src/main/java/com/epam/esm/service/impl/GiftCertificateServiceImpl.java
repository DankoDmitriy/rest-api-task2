package com.epam.esm.service.impl;

import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.IncorrectEntityException;
import com.epam.esm.exception.InputPagesParametersIncorrect;
import com.epam.esm.exception.UsedEntityException;
import com.epam.esm.service.dto.CustomPage;
import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.GiftCertificateSearchParams;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.GiftCertificateDao;
import com.epam.esm.repository.TagDao;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.PageSetup;
import com.epam.esm.util.PageCalculator;
import com.epam.esm.validator.GiftCertificateSearchParamsValidator;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.PaginationVerifier;
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
    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final GiftCertificateSearchParamsValidator giftCertificateSearchParamsValidator;
    private final GiftCertificateValidator giftCertificateValidator;
    private final TagValidator tagValidator;
    private final PageCalculator pageCalculator;
    private final PaginationVerifier paginationValidator;


    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
                                      TagDao tagDao,
                                      GiftCertificateSearchParamsValidator giftCertificateSearchParamsValidator,
                                      GiftCertificateValidator giftCertificateValidator,
                                      TagValidator tagValidator,
                                      PageCalculator pageCalculator,
                                      PaginationVerifier paginationValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.giftCertificateSearchParamsValidator = giftCertificateSearchParamsValidator;
        this.giftCertificateValidator = giftCertificateValidator;
        this.tagValidator = tagValidator;
        this.pageCalculator = pageCalculator;
        this.paginationValidator = paginationValidator;
    }

    @Override
    public CustomPage findAll(GiftCertificateSearchParams searchParams, PageSetup pageSetup) {
        Long rowsInDataBase = null;
        Integer startPosition = pageCalculator.calculator(pageSetup.getPage(), pageSetup.getSize());
        List<ValidationError> validationErrors = giftCertificateSearchParamsValidator.validateSearchParams(searchParams);
        CustomPage<GiftCertificate> customPage = new CustomPage<>();
        if (validationErrors.contains(ValidationError.FIND_ALL)) {
            rowsInDataBase = giftCertificateDao.countRowsInTable();
            if (paginationValidator.verifyPagination(rowsInDataBase, pageSetup.getPage(), startPosition)) {
                pageCalculator.calculator(customPage, pageSetup, rowsInDataBase);
                customPage.setItems(giftCertificateDao.findAll(startPosition, pageSetup.getSize()));
                return customPage;
            } else {
                throw new InputPagesParametersIncorrect(ValidationError.PROBLEM_WITH_INPUT_PARAMETERS);
            }
        } else {
            if (!validationErrors.isEmpty()) {
                throw new IncorrectEntityException(ValidationError.PROBLEM_WITH_INPUT_PARAMETERS, validationErrors);
            }
            rowsInDataBase = giftCertificateDao.countRowsInTable(searchParams);
            pageCalculator.calculator(customPage, pageSetup, rowsInDataBase);
            if (paginationValidator.verifyPagination(rowsInDataBase, pageSetup.getPage(), startPosition)) {
                customPage.setItems(giftCertificateDao.search(searchParams, startPosition, pageSetup.getSize()));
                return customPage;
            } else {
                throw new InputPagesParametersIncorrect(ValidationError.PROBLEM_WITH_INPUT_PARAMETERS);
            }
        }
//        return customPage;
    }

    @Override
    public GiftCertificate findById(Long id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(id);
        if (!optionalGiftCertificate.isPresent()) {
            throw new EntityNotFoundException(ValidationError.GIFT_CERTIFICATE_NOT_FOUND_BY_ID, id);
        }
        return optionalGiftCertificate.get();
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
            attachTagToGiftCertificate(giftCertificate.getTags());
        }
        return giftCertificate;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        GiftCertificate certificate = findById(id);
        if (!giftCertificateDao.isGiftCertificateUsedInOrder(id)) {
            giftCertificateDao.delete(certificate);
        } else {
            throw new UsedEntityException(id);
        }
    }

    @Override
    @Transactional
    public GiftCertificate update(Long id, GiftCertificate giftCertificate) {
        GiftCertificate updatedCertificate = findById(id);

        checkGiftCertificateBeforeUpdate(updatedCertificate, giftCertificate);

        if (giftCertificate.getTags() != null && giftCertificate.getTags().size() > 0) {
            attachTagToGiftCertificate(giftCertificate.getTags());
            updatedCertificate.setTags(giftCertificate.getTags());
        }
        updatedCertificate.setLastUpdateDate(LocalDateTime.now());
        return giftCertificateDao.update(updatedCertificate);
    }

    private void attachTagToGiftCertificate(List<Tag> tagItems) {
        for (Tag tag : tagItems) {
            tagDao.save(tag);
        }
    }

    private void checkGiftCertificateBeforeUpdate(
            GiftCertificate updatedCertificate,
            GiftCertificate certificate) {
        List<ValidationError> validationErrors = new ArrayList<>();

        if (certificate.getName() != null
                && !certificate.getName().equals(updatedCertificate.getName())) {
            giftCertificateValidator.validateName(certificate.getName(), validationErrors);
            updatedCertificate.setName(certificate.getName());
        }

        if (certificate.getDescription() != null
                && !certificate.getDescription().equals(updatedCertificate.getDescription())) {
            giftCertificateValidator.validateDescription(certificate.getDescription(), validationErrors);
            updatedCertificate.setDescription(certificate.getDescription());
        }

        if (certificate.getDuration() != null
                && !certificate.getDuration().equals(updatedCertificate.getDuration())) {
            giftCertificateValidator.validateDuration(certificate.getDuration(), validationErrors);
            updatedCertificate.setDuration(certificate.getDuration());
        }

        if (certificate.getPrice() != null
                && !certificate.getPrice().equals(updatedCertificate.getPrice())) {
            giftCertificateValidator.validatePrice(certificate.getPrice(), validationErrors);
            updatedCertificate.setPrice(certificate.getPrice());
        }
        validationErrors.addAll(tagValidator.validateTagNameList(certificate.getTags()));

        if (!validationErrors.isEmpty()) {
            throw new IncorrectEntityException(ValidationError.PROBLEM_WITH_INPUT_PARAMETERS, validationErrors);
        }
    }
}
