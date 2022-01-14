package com.epam.esm.validator;

import org.springframework.stereotype.Component;

@Component
public class PaginationVerifier {
    public boolean verifyPagination(Long rowsInDataBase, Integer pageNumber, Integer startPosition) {
        if (pageNumber < 0) {
            return false;
        }
        return (rowsInDataBase == 0 || startPosition < rowsInDataBase);
    }
}
