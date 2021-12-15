package com.epam.esm.service.impl;

import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.TagDao;
import com.epam.esm.service.TagService;
import com.epam.esm.data_provider.TagProvider;
import com.epam.esm.data_provider.ValidationErrorsProvider;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.IncorrectEntityException;
import com.epam.esm.service.impl.TagServiceImpl;
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
public class TagServiceImplTest {
    private Long testTagId = 1L;
    private String testIncorrectName = ".,-*";
    private String testCorrectName = "Tag name";

    @InjectMocks
    private TagServiceImpl service;

    @Mock
    private TagDao tagDaoMock;

    @Mock
    private TagValidator tagValidatorMock;

    private final TagProvider tagProvider = new TagProvider();
    private final ValidationErrorsProvider errorsProvider = new ValidationErrorsProvider();

    @Test
    void findAllTest() {
        List<Tag> expected = tagProvider.getTagList();
        Mockito.when(tagDaoMock.findAll()).thenReturn(expected);
        List<Tag> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findByIdPositiveTest() {
        Optional<Tag> expected = Optional.of(tagProvider.getTag());
        Mockito.when(tagDaoMock.findById(testTagId)).thenReturn(expected);
        Optional<Tag> actual = service.findById(expected.get().getId());
        assertEquals(expected, actual);
    }

    @Test
    void findByIdNegativeTest() {
        Optional<Tag> expected = Optional.empty();
        Tag tag = tagProvider.getTag();
        Mockito.when(tagDaoMock.findById(tag.getId())).thenReturn(expected);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.findById(testTagId));
        assertEquals(testTagId, exception.getId());
    }

    @Test
    void savePositiveTest() {
        Tag tag = tagProvider.getTagWithOutId();
        Tag expected = tagProvider.getTag();
        Mockito.when(tagDaoMock.save(tag)).thenReturn(expected);
        Tag actual = service.save(tag);
        assertEquals(expected, actual);
    }

    @Test
    void savePNegativeTest() {
        Tag tag = new Tag();
        tag.setName("a.");
        TagService service = new TagServiceImpl(tagDaoMock, new TagValidator());
        List<ValidationError> expected = errorsProvider.getErrorTagNameHasIncorrectSymbol();
        IncorrectEntityException exception = assertThrows(IncorrectEntityException.class,
                () -> service.save(tag));
        assertEquals(expected, exception.getValidationErrors());
    }
}
