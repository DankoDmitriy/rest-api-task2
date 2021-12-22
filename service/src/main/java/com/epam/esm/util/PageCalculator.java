package com.epam.esm.util;

import org.springframework.stereotype.Component;

@Component
public class PageCalculator {
    public Integer calculator(Integer pageNumber, Integer rowsOnPage) {
        return pageNumber * rowsOnPage;
    }
}
