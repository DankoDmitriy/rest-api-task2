package com.epam.esm.service.impl;

import com.epam.esm.constant.ErrorMessagesConstant;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.impl.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.DtoToEntityConverterService;
import com.epam.esm.service.EntityToDtoConverterService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.CustomPageDto;
import com.epam.esm.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DtoToEntityConverterService dtoToEntityConverterService;
    private final EntityToDtoConverterService entityToDtoConverterService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           DtoToEntityConverterService dtoToEntityConverterService,
                           EntityToDtoConverterService entityToDtoConverterService) {
        this.userRepository = userRepository;
        this.dtoToEntityConverterService = dtoToEntityConverterService;
        this.entityToDtoConverterService = entityToDtoConverterService;
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> optionalTag = userRepository.findById(id);
        if (!optionalTag.isPresent()) {
            throw new EntityNotFoundException(ErrorMessagesConstant.USER_NOT_FOUND_BY_ID, id);
        } else {
            return entityToDtoConverterService.convert(optionalTag.get());
        }
    }

    @Override
    public UserDto save(UserDto user) {
        throw new UnsupportedOperationException(ErrorMessagesConstant.UN_SUPPORTED_OPERATION);
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException(ErrorMessagesConstant.UN_SUPPORTED_OPERATION);
    }

    @Override
    public CustomPageDto findAllUsersPage(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
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
}
