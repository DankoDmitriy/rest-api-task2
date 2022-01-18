package com.epam.esm.service.impl;

import com.epam.esm.data_provider.UserDtoProvider;
import com.epam.esm.data_provider.UserProvider;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.impl.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.DtoToEntityConverterService;
import com.epam.esm.service.EntityToDtoConverterService;
import com.epam.esm.service.converter.DtoToUserConverter;
import com.epam.esm.service.dto.CustomPageDto;
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
public class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DtoToEntityConverterService dtoToEntityConverterService;

    @Mock
    private EntityToDtoConverterService entityToDtoConverterService;

    private final UserDtoProvider userDtoProvider = new UserDtoProvider();
    private final UserProvider userProvider = new UserProvider();

    @Test
    public void findByIdPositiveTest() {
        DtoToUserConverter converter = new DtoToUserConverter();
        UserDto expected = userDtoProvider.getUser();
        Mockito.when(userRepository.findById(expected.getId())).thenReturn(Optional.of(converter.convert(expected)));
        Mockito.when(entityToDtoConverterService.convert(converter.convert(expected))).thenReturn(expected);
        UserDto actual = service.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void findByIdNegativeTest() {
        Optional<User> expected = Optional.empty();
        UserDto user = userDtoProvider.getUser();
        Mockito.when(userRepository.findById(user.getId())).thenReturn(expected);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.findById(user.getId()));
        assertEquals(user.getId(), exception.getId());
    }

    @Test
    public void findAllPositiveTest() {
        List<User> users = userProvider.getUserList();
        List<UserDto> usersDto = userDtoProvider.getUserList();
        Page<User> usersPage = new PageImpl<>(users);
        Pageable pageable = PageRequest.of(0, 10);
        CustomPageDto<UserDto> expected = new CustomPageDto(10, 1L, 1, 0, usersDto);

        Mockito.when(userRepository.findAll(pageable)).thenReturn(usersPage);
        for (int i = 0; i < users.size(); i++) {
            Mockito.when(entityToDtoConverterService.convert(users.get(i))).thenReturn(usersDto.get(i));
        }

        CustomPageDto actual = service.findAllUsersPage(pageable);

        assertEquals(expected, actual);
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
