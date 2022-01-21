package com.epam.esm.service.impl;

import com.epam.esm.data_provider.GiftCertificateDtoProvider;
import com.epam.esm.data_provider.GiftCertificateProvider;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.GiftCertificateSearchParams;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.specification.GiftCertificationSpecificationFactory;
import com.epam.esm.service.DtoToEntityConverterService;
import com.epam.esm.service.EntityToDtoConverterService;
import com.epam.esm.service.converter.DtoToGiftCertificateConverter;
import com.epam.esm.service.converter.DtoToTagConverter;
import com.epam.esm.service.dto.CustomPageDto;
import com.epam.esm.service.dto.GiftCertificateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceImplTest {

    @InjectMocks
    private GiftCertificateServiceImpl service;

    @Mock
    private GiftCertificateRepository certificateRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DtoToEntityConverterService dtoToEntityConverterService;

    @Mock
    private EntityToDtoConverterService entityToDtoConverterService;

    @Mock
    private GiftCertificationSpecificationFactory certificationSpecificationFactory;

    private final GiftCertificateProvider certificateProvider = new GiftCertificateProvider();
    private final GiftCertificateDtoProvider certificateDtoProvider = new GiftCertificateDtoProvider();
    private final GiftCertificationSpecificationFactory specificationFactory = new GiftCertificationSpecificationFactory();

    @Test
    void findAllPositiveTest() {
        List<GiftCertificate> giftCertificates = certificateProvider.getList();
        List<GiftCertificateDto> giftCertificatesDto = certificateDtoProvider.getList();
        Page<GiftCertificate> page = new PageImpl<>(giftCertificates);
        GiftCertificateSearchParams searchParams = new GiftCertificateSearchParams();
        Pageable pageable = PageRequest.of(0, 10);
        CustomPageDto<GiftCertificateDto> expected =
                new CustomPageDto(10, 1L, 1, 0, giftCertificatesDto);
        Specification<GiftCertificate> specification = specificationFactory.createSpecification(searchParams);

        Mockito.when(certificateRepository.findAll(specification, pageable)).thenReturn(page);
        for (int i = 0; i < giftCertificates.size(); i++) {
            Mockito.when(entityToDtoConverterService.convert(giftCertificates.get(i))).
                    thenReturn(giftCertificatesDto.get(i));
        }

        Mockito.when(certificationSpecificationFactory.createSpecification(searchParams)).thenReturn(null);

        CustomPageDto actual = service.findAll(searchParams, pageable);

        assertEquals(expected, actual);
    }

    @Test
    void findByIdPositiveTest() {
        DtoToGiftCertificateConverter converter = new DtoToGiftCertificateConverter(new DtoToTagConverter());
        GiftCertificateDto expected = certificateDtoProvider.getCorrectGiftCertificate();
        GiftCertificate certificate = certificateProvider.getCorrectGiftCertificate();

        Mockito.when(certificateRepository.findById(expected.getId())).thenReturn(Optional.of(certificate));
        Mockito.when(entityToDtoConverterService.convert(converter.convert(expected))).thenReturn(expected);

        GiftCertificateDto actual = service.findById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    void findByIdNegativeTest() {
        Optional<GiftCertificate> expected = Optional.empty();
        GiftCertificateDto giftCertificateDto = certificateDtoProvider.getCorrectGiftCertificate();

        Mockito.when(certificateRepository.findById(giftCertificateDto.getId())).thenReturn(expected);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.findById(giftCertificateDto.getId()));

        assertEquals(giftCertificateDto.getId(), exception.getId());
    }

    @Test
    void savePositiveTest() {
        GiftCertificate certificateWithOutId = certificateProvider.getCorrectGiftCertificateWithOutId();
        GiftCertificate certificate = certificateProvider.getCorrectGiftCertificate();
        GiftCertificateDto certificateDtoTagWithOutId = certificateDtoProvider.getCorrectGiftCertificateWithOutId();
        GiftCertificateDto expected = certificateDtoProvider.getCorrectGiftCertificate();

        Mockito.when(certificateRepository.save(certificateWithOutId)).thenReturn(certificate);

        Mockito.when(dtoToEntityConverterService.convert(certificateDtoTagWithOutId)).thenReturn(certificateWithOutId);
        Mockito.when(entityToDtoConverterService.convert(Mockito.any(GiftCertificate.class))).thenReturn(expected);

        GiftCertificateDto actual = service.save(certificateDtoTagWithOutId);
        assertEquals(expected, actual);
    }

    @Test
    void updatePositiveTest() {
        GiftCertificate certificateWithOutId = certificateProvider.getCorrectGiftCertificateWithOutId();
        GiftCertificate certificate = certificateProvider.getCorrectGiftCertificate();
        GiftCertificateDto certificateDtoTagWithOutId = certificateDtoProvider.getCorrectGiftCertificateWithOutId();
        GiftCertificateDto expected = certificateDtoProvider.getCorrectGiftCertificate();

        Mockito.when(certificateRepository.findById(certificate.getId())).thenReturn(Optional.of(certificate));
        Mockito.when(dtoToEntityConverterService.convert(certificateDtoTagWithOutId)).thenReturn(certificateWithOutId);
        for (int i = 0; i < certificateDtoTagWithOutId.getTagDtoList().size(); i++) {
            Mockito.when(dtoToEntityConverterService.convert(certificateDtoTagWithOutId.getTagDtoList().get(i)))
                    .thenReturn(certificateWithOutId.getTags().get(i));
        }
        Mockito.when(certificateRepository.save(Mockito.any())).thenReturn(certificate);
        Mockito.when(entityToDtoConverterService.convert(certificate)).thenReturn(expected);


        GiftCertificateDto actual = service.update(certificate.getId(), certificateDtoTagWithOutId);
        assertEquals(expected, actual);
    }

    @Test
    void updateEntityNotFoundExceptionTest() {
        GiftCertificateDto giftCertificateDto = certificateDtoProvider.getCorrectGiftCertificate();

        Mockito.when(certificateRepository.findById(giftCertificateDto.getId())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                service.update(giftCertificateDto.getId(), giftCertificateDto));

        assertEquals(giftCertificateDto.getId(), exception.getId());
    }
}
