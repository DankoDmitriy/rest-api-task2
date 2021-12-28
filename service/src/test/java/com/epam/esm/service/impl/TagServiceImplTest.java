package com.epam.esm.service.impl;

import com.epam.esm.data_provider.CustomPageProvider;
import com.epam.esm.data_provider.TagProvider;
import com.epam.esm.data_provider.ValidationErrorsProvider;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.IncorrectEntityException;
import com.epam.esm.model.impl.CustomPage;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.TagDao;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.PageSetup;
import com.epam.esm.util.PageCalculator;
import com.epam.esm.validator.PaginationValidator;
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
    private final Long testTagId = 1L;
    private final String testIncorrectName = ".,-*";
    private final String testCorrectName = "Tag name";

    @InjectMocks
    private TagServiceImpl service;

    @Mock
    private TagDao tagDaoMock;

    @Mock
    private TagValidator tagValidatorMock;

    @Mock
    private PageCalculator pageCalculatorMock;

    @Mock
    private PaginationValidator paginationValidatorMock;

    private final TagProvider tagProvider = new TagProvider();
    private final ValidationErrorsProvider errorsProvider = new ValidationErrorsProvider();
    private final CustomPageProvider customPageProvider = new CustomPageProvider();

    @Test
    void findAllTest() {
        CustomPage<Tag> expected = customPageProvider.getCustomPageTag();
        Integer startPosition = expected.getItems().size();
        PageSetup setup = new PageSetup();
        setup.setPage(1);
        setup.setSize(10);

        Mockito.when(tagDaoMock.rowsInTable()).thenReturn(Long.valueOf(expected.getItems().size()));
        Mockito.when(pageCalculatorMock.calculator(setup.getPage(), setup.getSize())).thenReturn(0);
        Mockito.when(paginationValidatorMock.validate(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
        Mockito.when(tagDaoMock.findAll(0, 10)).thenReturn(expected.getItems());

        CustomPage<Tag> actual = service.findAll(setup);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdPositiveTest() {
        Tag expected = tagProvider.getTag();
        Mockito.when(tagDaoMock.findById(testTagId)).thenReturn(Optional.of(expected));
        Tag actual = service.findById(testTagId);
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
    void saveNegativeTest() {
        Tag tag = new Tag();
        tag.setName("a.");
        TagService service = new TagServiceImpl(tagDaoMock, new TagValidator(), pageCalculatorMock, paginationValidatorMock);
        List<ValidationError> expected = errorsProvider.getErrorTagNameHasIncorrectSymbol();
        IncorrectEntityException exception = assertThrows(IncorrectEntityException.class,
                () -> service.save(tag));
        assertEquals(expected, exception.getValidationErrors());
    }
}
