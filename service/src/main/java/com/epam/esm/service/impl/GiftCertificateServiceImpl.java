package com.epam.esm.service.impl;

import com.epam.esm.constant.ErrorMessagesConstant;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.UsedEntityException;
import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.GiftCertificateSearchParams;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.specification.GiftCertificationSpecificationFactory;
import com.epam.esm.service.DtoToEntityConverterService;
import com.epam.esm.service.EntityToDtoConverterService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.CustomPageDto;
import com.epam.esm.service.dto.GiftCertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final OrderRepository orderRepository;
    private final DtoToEntityConverterService dtoToEntityConverterService;
    private final EntityToDtoConverterService entityToDtoConverterService;
    private final GiftCertificationSpecificationFactory certificationSpecificationFactory;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository certificateRepository,
                                      TagRepository tagRepository,
                                      OrderRepository orderRepository,
                                      DtoToEntityConverterService dtoToEntityConverterService,
                                      EntityToDtoConverterService entityToDtoConverterService,
                                      GiftCertificationSpecificationFactory certificationSpecificationFactory) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.orderRepository = orderRepository;
        this.dtoToEntityConverterService = dtoToEntityConverterService;
        this.entityToDtoConverterService = entityToDtoConverterService;
        this.certificationSpecificationFactory = certificationSpecificationFactory;
    }

    @Override
    public CustomPageDto findAll(GiftCertificateSearchParams searchParams, Pageable pageable) {
        Page<GiftCertificate> page = certificateRepository.findAll(
                certificationSpecificationFactory.createSpecification(searchParams), pageable);
        return CustomPageDto.builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .number(page.getNumber())
                .size(pageable.getPageSize())
                .items(
                        page.getContent()
                                .stream()
                                .map(entityToDtoConverterService::convert)
                                .collect(Collectors.toList()))
                .build();
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        return entityToDtoConverterService.convert(findCertificateById(id));
    }

    @Override
    @Transactional
    public GiftCertificateDto save(GiftCertificateDto giftCertificateDto) {
        GiftCertificate certificate = dtoToEntityConverterService.convert(giftCertificateDto);
        LocalDateTime time = LocalDateTime.now();
        certificate.setCreateDate(time);
        certificate.setLastUpdateDate(time);
        certificateRepository.save(certificate);
        return entityToDtoConverterService.convert(certificate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        GiftCertificate certificate = findCertificateById(id);
        if (orderRepository.findFirstByCertificates_id(id).isPresent()) {
            throw new UsedEntityException(id);
        }
        certificateRepository.delete(certificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto update(Long id, GiftCertificateDto giftCertificate) {
        GiftCertificate updatedCertificate = findCertificateById(id);

        checkGiftCertificateBeforeUpdate(updatedCertificate, dtoToEntityConverterService.convert(giftCertificate));

        if (giftCertificate.getTagDtoList() != null && giftCertificate.getTagDtoList().size() > 0) {
            List<Tag> tags = giftCertificate.getTagDtoList()
                    .stream().map(dtoToEntityConverterService::convert).collect(Collectors.toList());
            attachTagToGiftCertificate(tags);
            updatedCertificate.setTags(tags);
        }
        updatedCertificate.setLastUpdateDate(LocalDateTime.now());
        return entityToDtoConverterService.convert(certificateRepository.save(updatedCertificate));
    }

    private void attachTagToGiftCertificate(List<Tag> tagItems) {
        tagRepository.saveAll(tagItems);
    }

    private void checkGiftCertificateBeforeUpdate(GiftCertificate updatedCertificate, GiftCertificate certificate) {
        if (certificate.getName() != null
                && !certificate.getName().equals(updatedCertificate.getName())) {
            updatedCertificate.setName(certificate.getName());
        }

        if (certificate.getDescription() != null
                && !certificate.getDescription().equals(updatedCertificate.getDescription())) {
            updatedCertificate.setDescription(certificate.getDescription());
        }

        if (certificate.getDuration() != null
                && !certificate.getDuration().equals(updatedCertificate.getDuration())) {
            updatedCertificate.setDuration(certificate.getDuration());
        }

        if (certificate.getPrice() != null
                && !certificate.getPrice().equals(updatedCertificate.getPrice())) {
            updatedCertificate.setPrice(certificate.getPrice());
        }
    }

    private GiftCertificate findCertificateById(Long id) {
        Optional<GiftCertificate> optionalTag = certificateRepository.findById(id);
        if (!optionalTag.isPresent()) {
            throw new EntityNotFoundException(ErrorMessagesConstant.GIFT_CERTIFICATE_NOT_FOUND_BY_ID, id);
        } else {
            return optionalTag.get();
        }
    }
}
