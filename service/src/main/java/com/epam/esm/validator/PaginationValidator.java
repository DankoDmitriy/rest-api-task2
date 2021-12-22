package com.epam.esm.validator;

import org.springframework.stereotype.Component;

@Component
public class PaginationValidator {
    public boolean validate(Long rowsInDataBase, Integer pageNumber, Integer startPosition) {
        if (pageNumber < 0 || rowsInDataBase <= startPosition) {
            return false;
        }
        return true;
    }
}
