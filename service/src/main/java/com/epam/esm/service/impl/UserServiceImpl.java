package com.epam.esm.service.impl;

import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InputPagesParametersIncorrect;
import com.epam.esm.model.impl.CustomPage;
import com.epam.esm.model.impl.User;
import com.epam.esm.repository.UserDao;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.PageSetup;
import com.epam.esm.util.PageCalculator;
import com.epam.esm.validator.PaginationValidator;
import com.epam.esm.validator.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PageCalculator pageCalculator;
    private final PaginationValidator paginationValidator;

    @Autowired
    public UserServiceImpl(UserDao userDao, PageCalculator pageCalculator, PaginationValidator paginationValidator) {
        this.userDao = userDao;
        this.pageCalculator = pageCalculator;
        this.paginationValidator = paginationValidator;
    }

    @Override
    public User findById(Long id) {
        Optional<User> optionalUser = userDao.findById(id);
        if (!optionalUser.isPresent()) {
            throw new EntityNotFoundException(ValidationError.USER_NOT_FOUND_BY_ID, id);
        }
        return optionalUser.get();
    }

    @Override
    public User save(User user) {
        throw new UnsupportedOperationException(ValidationError.UN_SUPPORTED_OPERATION.name());
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException(ValidationError.UN_SUPPORTED_OPERATION.name());
    }

    @Override
    public CustomPage findAll(PageSetup pageSetup) {
        CustomPage<User> customPage = new CustomPage<>();
        Long rowsInDataBase = userDao.rowsInTable();
        Integer startPosition = pageCalculator.calculator(pageSetup.getPage(), pageSetup.getSize());
        if (paginationValidator.validate(rowsInDataBase, pageSetup.getPage(), startPosition)) {
            pageCalculator.calculator(customPage, pageSetup, rowsInDataBase);
            customPage.setItems(userDao.findAll(startPosition, pageSetup.getSize()));
            return customPage;
        } else {
            throw new InputPagesParametersIncorrect(ValidationError.PROBLEM_WITH_INPUT_PARAMETERS);
        }
    }
}
