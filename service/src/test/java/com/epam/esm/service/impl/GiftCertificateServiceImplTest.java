package com.epam.esm.service.impl;

import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.GiftCertificateSearchParams;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.GiftCertificateDao;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.repository.TagDao;
import com.epam.esm.data_provider.GiftCertificateProvider;
import com.epam.esm.data_provider.SearchParamsProvider;
import com.epam.esm.data_provider.TagProvider;
import com.epam.esm.data_provider.ValidationErrorsProvider;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.IncorrectEntityException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.validator.GiftCertificateSearchParamsValidator;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceImplTest {

    @InjectMocks
    private GiftCertificateServiceImpl service;

    @Mock
    private GiftCertificateDao certificateDaoMock;

    @Mock
    private TagDao tagDaoMock;

    @Mock
    private GiftCertificateValidator certificateValidatorMock;

    @Mock
    private TagValidator tagValidatorMock;

    @Mock
    private GiftCertificateSearchParamsValidator paramsValidatorMock;

    private final GiftCertificateProvider certificateProvider = new GiftCertificateProvider();
    private final SearchParamsProvider searchParamsProvider = new SearchParamsProvider();
    private final ValidationErrorsProvider validationErrorsProvider = new ValidationErrorsProvider();
    private final TagProvider tagProvider = new TagProvider();

    @Test
    void findAllDaoFindAllPositiveTest() {
        List<GiftCertificate> expected = certificateProvider.getEmptyList();
        GiftCertificateSearchParams searchParams = searchParamsProvider.getEmptyParameters();
        List<ValidationError> validationErrors = validationErrorsProvider.getFindAllErrors();

        Mockito.when(paramsValidatorMock.validateSearchParams(searchParams))
                .thenReturn(validationErrors);
        Mockito.when(certificateDaoMock.findAll())
                .thenReturn(expected);
        List<GiftCertificate> actual = service.findAll(searchParams);
        assertEquals(expected, actual);
    }

    @Test
    void findAllDaoSearchPositiveTest() {
        List<GiftCertificate> expected = certificateProvider.getEmptyList();
        GiftCertificateSearchParams searchParams = searchParamsProvider.getParametersByTagName();
        List<ValidationError> validationErrors = validationErrorsProvider.getEmptyErrors();
        Mockito.when(paramsValidatorMock.validateSearchParams(searchParams))
                .thenReturn(validationErrors);
        Mockito.when(certificateDaoMock.search(searchParams))
                .thenReturn(expected);
        List<GiftCertificate> actual = service.findAll(searchParams);
        assertEquals(expected, actual);
    }

    @Test
    void findAllNegativeTest() {
        GiftCertificateSearchParams searchParams = searchParamsProvider.getEmptyParameters();
        List<ValidationError> validationErrors = validationErrorsProvider.getErrorIncorrectTagNameSearch();
        Mockito.when(paramsValidatorMock.validateSearchParams(searchParams))
                .thenReturn(validationErrors);
        assertThrows(IncorrectEntityException.class, () -> service.findAll(searchParams));
    }

    @Test
    void findByIdPositiveTest() {
        GiftCertificate expected = certificateProvider.getCorrectGiftCertificate();
        Long id = expected.getId();
        Mockito.when(certificateDaoMock.findById(id))
                .thenReturn(Optional.of(expected));
        GiftCertificate actual = service.findById(id).get();
        assertEquals(expected, actual);
    }

    @Test
    void findByIdNegativeTest() {
        Long id = 1L;
        Mockito.when(certificateDaoMock.findById(id))
                .thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.findById(id));
        assertEquals(id, exception.getId());
    }

    @Test
    void savePositiveTest() {
        GiftCertificate expected = certificateProvider.getCorrectGiftCertificate();
        List<ValidationError> validationErrors = validationErrorsProvider.getEmptyErrors();
        Tag tag = tagProvider.getTag();
        Mockito.when(certificateValidatorMock.validateCertificate(expected))
                .thenReturn(validationErrors);
        Mockito.when(tagValidatorMock.validateTagNameList(expected.getTags()))
                .thenReturn(validationErrors);
        Mockito.when(certificateDaoMock.save(expected))
                .thenReturn(expected);
        Mockito.when(tagDaoMock.save(tag)).thenReturn(tag);
        GiftCertificate actual = service.save(expected);
        assertEquals(expected, actual);
    }

    @Test
    void saveNegativeTest() {
        GiftCertificate certificate = certificateProvider.getCorrectGiftCertificate();
        List<ValidationError> expected = validationErrorsProvider.getErrorGiftCertificateNameIsEmptyOrNull();
        Mockito.when(certificateValidatorMock.validateCertificate(certificate))
                .thenReturn(expected);
        IncorrectEntityException exception = assertThrows(IncorrectEntityException.class, () -> service.save(certificate));
        assertEquals(expected, exception.getValidationErrors());
    }

    @Test
    void updatePositiveTest() {
        GiftCertificate expected = certificateProvider.getCorrectGiftCertificate();
        GiftCertificate updateGiftCertificate = certificateProvider.getCorrectGiftCertificateWithOutId();
        Tag tag = tagProvider.getTag();
        Mockito.when(certificateDaoMock.findById(expected.getId())).thenReturn(Optional.of(expected));
        Mockito.when(certificateDaoMock.update(updateGiftCertificate)).thenReturn(expected);
        Mockito.when(tagDaoMock.save(Mockito.any())).thenReturn(tag);
        GiftCertificate actual = service.update(expected.getId(), updateGiftCertificate);
        assertEquals(expected, actual);
    }

    @Test
    void updateIncorrectEntityExceptionTest() {
        GiftCertificate giftFromDb = certificateProvider.getCorrectGiftCertificate();
        GiftCertificate updateGiftCertificate = certificateProvider.getCorrectGiftCertificateWithOutId();
        updateGiftCertificate.setName("a.");

        GiftCertificateService service = new GiftCertificateServiceImpl(
                certificateDaoMock,
                tagDaoMock,
                paramsValidatorMock,
                new GiftCertificateValidator(),
                tagValidatorMock
        );
        List<ValidationError> expected = validationErrorsProvider.getErrorGiftCertificateNameHasIncorrectSymbol();
        Mockito.when(certificateDaoMock.findById(giftFromDb.getId())).thenReturn(Optional.of(giftFromDb));
        IncorrectEntityException actual = assertThrows(IncorrectEntityException.class, () ->
                service.update(giftFromDb.getId(), updateGiftCertificate));
        assertEquals(expected, actual.getValidationErrors());
    }

    @Test
    void updateEntityNotFoundExceptionTest() {
        GiftCertificate giftFromDb = certificateProvider.getCorrectGiftCertificate();
        Mockito.when(certificateDaoMock.findById(giftFromDb.getId())).thenReturn(Optional.empty());
        EntityNotFoundException actual = assertThrows(EntityNotFoundException.class, () ->
                service.update(giftFromDb.getId(), giftFromDb));
        assertEquals(giftFromDb.getId(), actual.getId());
    }
}
