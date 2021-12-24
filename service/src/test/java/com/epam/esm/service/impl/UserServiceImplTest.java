package com.epam.esm.service.impl;

import com.epam.esm.data_provider.UserProvider;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InputPagesParametersIncorrect;
import com.epam.esm.model.impl.User;
import com.epam.esm.repository.UserDao;
import com.epam.esm.service.dto.PageSetup;
import com.epam.esm.util.PageCalculator;
import com.epam.esm.validator.PaginationValidator;
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
public class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl service;

    @Mock
    private UserDao userDaoMock;

    @Mock
    private PageCalculator pageCalculatorMock;

    @Mock
    private PaginationValidator paginationValidatorMock;

    private final UserProvider provider = new UserProvider();

    @Test
    public void findByIdPositiveTest() {
        User expected = provider.getUser();
        Mockito.when(userDaoMock.findById(expected.getId())).thenReturn(Optional.of(expected));
        User actual = service.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void findByIdNegativeTest() {
        Optional<User> expected = Optional.empty();
        User user = provider.getUser();
        Mockito.when(userDaoMock.findById(user.getId())).thenReturn(expected);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.findById(user.getId()));
        assertEquals(user.getId(), exception.getId());
    }

    @Test
    public void findAllPositiveTest() {
        List<User> expected = provider.getUserList();
        Integer startPosition = expected.size();
        PageSetup setup = new PageSetup();
        setup.setPage(1);
        setup.setSize(10);

        Mockito.when(userDaoMock.rowsInTable()).thenReturn(Long.valueOf(expected.size()));
        Mockito.when(pageCalculatorMock.calculator(setup.getPage(), setup.getSize())).thenReturn(0);
        Mockito.when(paginationValidatorMock.validate(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
        Mockito.when(userDaoMock.findAll(0, 10)).thenReturn(expected);

        List<User> actual = service.findAll(setup);
        assertEquals(expected, actual);
    }

    @Test
    public void findAllNegativeTest() {
        List<User> expected = provider.getUserList();
        Integer startPosition = expected.size();
        PageSetup setup = new PageSetup();
        setup.setPage(-1);
        setup.setSize(-10);

        Mockito.when(paginationValidatorMock.validate(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(false);

        assertThrows(InputPagesParametersIncorrect.class, () -> service.findAll(setup));
    }

    @Test
    public void saveTest() {
        assertThrows(UnsupportedOperationException.class, () -> service.save(null));
    }

    @Test
    public void deleteTest() {
        assertThrows(UnsupportedOperationException.class, () -> service.delete(null));
    }
}
