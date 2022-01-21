package com.epam.esm.service.impl;

import com.epam.esm.constant.ErrorMessagesConstant;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.UsedEntityException;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.DtoToEntityConverterService;
import com.epam.esm.service.EntityToDtoConverterService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.CustomPageDto;
import com.epam.esm.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final GiftCertificateRepository certificateRepository;
    private final DtoToEntityConverterService dtoToEntityConverterService;
    private final EntityToDtoConverterService entityToDtoConverterService;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository,
                          GiftCertificateRepository certificateRepository,
                          DtoToEntityConverterService dtoToEntityConverterService,
                          EntityToDtoConverterService entityToDtoConverterService) {
        this.tagRepository = tagRepository;
        this.certificateRepository = certificateRepository;
        this.dtoToEntityConverterService = dtoToEntityConverterService;
        this.entityToDtoConverterService = entityToDtoConverterService;
    }

    @Override
    public CustomPageDto findAllTagsPage(Pageable pageable) {
        Page<Tag> page = tagRepository.findAll(pageable);
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
    public TagDto findById(Long id) {
        return entityToDtoConverterService.convert(findTagById(id));
    }

    @Override
    public TagDto save(TagDto tag) {
        return entityToDtoConverterService.convert(tagRepository.save(dtoToEntityConverterService.convert(tag)));
    }

    @Override
    public void delete(Long id) {
        Tag tag = findTagById(id);
        if (certificateRepository.findFirstByTags_Id(id).isPresent()) {
            throw new UsedEntityException(id);
        }
        tagRepository.delete(tag);
    }

    private Tag findTagById(Long id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (!optionalTag.isPresent()) {
            throw new EntityNotFoundException(ErrorMessagesConstant.TAG_NOT_FOUND_BY_ID, id);
        } else {
            return optionalTag.get();
        }
    }
}
