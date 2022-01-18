package com.epam.esm.service.impl;

import com.epam.esm.data_provider.TagDtoProvider;
import com.epam.esm.data_provider.TagProvider;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.DtoToEntityConverterService;
import com.epam.esm.service.EntityToDtoConverterService;
import com.epam.esm.service.converter.DtoToTagConverter;
import com.epam.esm.service.dto.CustomPageDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UserDto;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl service;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private GiftCertificateRepository certificateRepository;

    @Mock
    private DtoToEntityConverterService dtoToEntityConverterService;

    @Mock
    private EntityToDtoConverterService entityToDtoConverterService;

    private final TagProvider tagProvider = new TagProvider();
    private final TagDtoProvider tagDtoProvider = new TagDtoProvider();

    @Test
    void findAllTest() {
        List<Tag> tags = tagProvider.getTagList();
        List<TagDto> tagsDto = tagDtoProvider.getTagList();
        Page<Tag> usersPage = new PageImpl<>(tags);
        Pageable pageable = PageRequest.of(0, 10);
        CustomPageDto<UserDto> expected = new CustomPageDto(10, 1L, 1, 0, tagsDto);

        Mockito.when(tagRepository.findAll(pageable)).thenReturn(usersPage);
        for (int i = 0; i < tags.size(); i++) {
            Mockito.when(entityToDtoConverterService.convert(tags.get(i))).thenReturn(tagsDto.get(i));
        }

        CustomPageDto actual = service.findAllTagsPage(pageable);

        assertEquals(expected, actual);
    }

    @Test
    void findByIdPositiveTest() {
        DtoToTagConverter converter = new DtoToTagConverter();
        TagDto expected = tagDtoProvider.getTag();
        Tag tag = tagProvider.getTag();

        Mockito.when(tagRepository.findById(expected.getId())).thenReturn(Optional.of(tag));
        Mockito.when(entityToDtoConverterService.convert(converter.convert(expected))).thenReturn(expected);

        TagDto actual = service.findById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    void findByIdNegativeTest() {
        Optional<Tag> expected = Optional.empty();
        TagDto tagDto = tagDtoProvider.getTag();

        Mockito.when(tagRepository.findById(tagDto.getId())).thenReturn(expected);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.findById(tagDto.getId()));

        assertEquals(tagDto.getId(), exception.getId());
    }

    @Test
    void savePositiveTest() {
        Tag tagWithOutId = tagProvider.getTagWithOutId();
        Tag tag = tagProvider.getTag();
        TagDto tagDtoTagWithOutId = tagDtoProvider.getTagWithOutId();
        TagDto expected = tagDtoProvider.getTag();

        Mockito.when(tagRepository.save(tagWithOutId)).thenReturn(tag);
        Mockito.when(entityToDtoConverterService.convert(tag)).thenReturn(expected);
        Mockito.when(dtoToEntityConverterService.convert(tagDtoTagWithOutId)).thenReturn(tagWithOutId);

        TagDto actual = service.save(tagDtoTagWithOutId);
        assertEquals(expected, actual);
    }
}
