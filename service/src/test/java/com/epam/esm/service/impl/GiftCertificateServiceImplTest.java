package com.epam.esm.service.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceImplTest {

//    @InjectMocks
//    private GiftCertificateServiceImpl service;
//
//    @Mock
//    private GiftCertificateDao certificateDaoMock;
//
//    @Mock
//    private TagDao tagDaoMock;
//
//    @Mock
//    private GiftCertificateValidator certificateValidatorMock;
//
//    @Mock
//    private TagValidator tagValidatorMock;
//
//    @Mock
//    private PageCalculator pageCalculatorMock;
//
//    @Mock
//    private PaginationVerifier paginationValidatorMock;
//
//    @Mock
//    private GiftCertificateSearchParamsValidator paramsValidatorMock;
//
//    private final GiftCertificateProvider certificateProvider = new GiftCertificateProvider();
//    private final SearchParamsProvider searchParamsProvider = new SearchParamsProvider();
//    private final ValidationErrorsProvider validationErrorsProvider = new ValidationErrorsProvider();
//    private final TagProvider tagProvider = new TagProvider();
//    private final CustomPageProvider customPageProvider = new CustomPageProvider();

//    @Test
//    void findAllDaoFindAllPositiveTest() {
//        CustomPage<GiftCertificate> expected = customPageProvider.getCustomPageGiftCertificate();
//        Integer startPosition = expected.getItems().size();
//        PageSetup setup = new PageSetup();
//        setup.setPage(1);
//        setup.setSize(10);
//        GiftCertificateSearchParams searchParams = searchParamsProvider.getEmptyParameters();
//        List<ValidationError> validationErrors = validationErrorsProvider.getFindAllErrors();
//
//        Mockito.when(certificateDaoMock.countRowsInTable()).thenReturn(Long.valueOf(expected.getItems().size()));
//        Mockito.when(pageCalculatorMock.calculator(setup.getPage(), setup.getSize())).thenReturn(0);
//        Mockito.when(paginationValidatorMock.verifyPagination(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
//
//        Mockito.when(paramsValidatorMock.validateSearchParams(searchParams))
//                .thenReturn(validationErrors);
//        Mockito.when(certificateDaoMock.findAll(0, 10))
//                .thenReturn(expected.getItems());
//
//        CustomPage<GiftCertificate> actual = service.findAll(searchParams, setup);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void findAllDaoSearchPositiveTest() {
//        CustomPage<GiftCertificate> expected = customPageProvider.getCustomPageGiftCertificate();
//        Integer startPosition = expected.getItems().size();
//        PageSetup setup = new PageSetup();
//        setup.setPage(1);
//        setup.setSize(10);
//        GiftCertificateSearchParams searchParams = searchParamsProvider.getParametersByTagName();
//        List<ValidationError> validationErrors = validationErrorsProvider.getEmptyErrors();
//
//        Mockito.when(certificateDaoMock.countRowsInTable(searchParams)).thenReturn(Long.valueOf(expected.getItems().size()));
//        Mockito.when(pageCalculatorMock.calculator(setup.getPage(), setup.getSize())).thenReturn(0);
//        Mockito.when(paginationValidatorMock.verifyPagination(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
//
//        Mockito.when(paramsValidatorMock.validateSearchParams(searchParams))
//                .thenReturn(validationErrors);
//        Mockito.when(certificateDaoMock.search(searchParams, 0, 10))
//                .thenReturn(expected.getItems());
//
//        CustomPage<GiftCertificate> actual = service.findAll(searchParams, setup);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void findAllNegativeTest() {
//        GiftCertificateSearchParams searchParams = searchParamsProvider.getEmptyParameters();
//        List<ValidationError> validationErrors = validationErrorsProvider.getErrorIncorrectTagNameSearch();
//        Mockito.when(paramsValidatorMock.validateSearchParams(searchParams))
//                .thenReturn(validationErrors);
//        assertThrows(IncorrectEntityException.class, () -> service.findAll(searchParams, new PageSetup()));
//    }
//
//    @Test
//    void findByIdPositiveTest() {
//        GiftCertificate expected = certificateProvider.getCorrectGiftCertificate();
//        Long id = expected.getId();
//        Mockito.when(certificateDaoMock.findById(id))
//                .thenReturn(Optional.of(expected));
//        GiftCertificate actual = service.findById(id);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void findByIdNegativeTest() {
//        Long id = 1L;
//        Mockito.when(certificateDaoMock.findById(id))
//                .thenReturn(Optional.empty());
//        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.findById(id));
//        assertEquals(id, exception.getId());
//    }
//
//    @Test
//    void savePositiveTest() {
//        GiftCertificate expected = certificateProvider.getCorrectGiftCertificate();
//        List<ValidationError> validationErrors = validationErrorsProvider.getEmptyErrors();
//        Tag tag = tagProvider.getTag();
//        Mockito.when(certificateValidatorMock.validateCertificate(expected))
//                .thenReturn(validationErrors);
//        Mockito.when(tagValidatorMock.validateTagNameList(expected.getTags()))
//                .thenReturn(validationErrors);
//        Mockito.when(certificateDaoMock.save(expected))
//                .thenReturn(expected);
//        Mockito.when(tagDaoMock.save(tag)).thenReturn(tag);
//        GiftCertificate actual = service.save(expected);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void saveNegativeTest() {
//        GiftCertificate certificate = certificateProvider.getCorrectGiftCertificate();
//        List<ValidationError> expected = validationErrorsProvider.getErrorGiftCertificateNameIsEmptyOrNull();
//        Mockito.when(certificateValidatorMock.validateCertificate(certificate))
//                .thenReturn(expected);
//        IncorrectEntityException exception = assertThrows(IncorrectEntityException.class, () -> service.save(certificate));
//        assertEquals(expected, exception.getValidationErrors());
//    }
//
//    @Test
//    void updatePositiveTest() {
//        GiftCertificate expected = certificateProvider.getCorrectGiftCertificate();
//        GiftCertificate updateGiftCertificate = certificateProvider.getCorrectGiftCertificateWithOutId();
//        Tag tag = tagProvider.getTag();
//        Mockito.when(certificateDaoMock.findById(expected.getId())).thenReturn(Optional.of(expected));
//        Mockito.when(certificateDaoMock.update(Mockito.any())).thenReturn(expected);
//        Mockito.when(tagDaoMock.save(Mockito.any())).thenReturn(tag);
//        GiftCertificate actual = service.update(expected.getId(), updateGiftCertificate);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void updateIncorrectEntityExceptionTest() {
//        GiftCertificate giftFromDb = certificateProvider.getCorrectGiftCertificate();
//        GiftCertificate updateGiftCertificate = certificateProvider.getCorrectGiftCertificateWithOutId();
//        updateGiftCertificate.setName("a.");
//
//        GiftCertificateService service = new GiftCertificateServiceImpl(
//                certificateDaoMock,
//                tagDaoMock,
//                paramsValidatorMock,
//                new GiftCertificateValidator(),
//                tagValidatorMock,
//                pageCalculatorMock,
//                paginationValidatorMock
//        );
//        List<ValidationError> expected = validationErrorsProvider.getErrorGiftCertificateNameHasIncorrectSymbol();
//        Mockito.when(certificateDaoMock.findById(giftFromDb.getId())).thenReturn(Optional.of(giftFromDb));
//        IncorrectEntityException actual = assertThrows(IncorrectEntityException.class, () ->
//                service.update(giftFromDb.getId(), updateGiftCertificate));
//        assertEquals(expected, actual.getValidationErrors());
//    }
//
//    @Test
//    void updateEntityNotFoundExceptionTest() {
//        GiftCertificate giftFromDb = certificateProvider.getCorrectGiftCertificate();
//        Mockito.when(certificateDaoMock.findById(giftFromDb.getId())).thenReturn(Optional.empty());
//        EntityNotFoundException actual = assertThrows(EntityNotFoundException.class, () ->
//                service.update(giftFromDb.getId(), giftFromDb));
//        assertEquals(giftFromDb.getId(), actual.getId());
//    }
}
