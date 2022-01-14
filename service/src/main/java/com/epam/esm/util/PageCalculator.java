package com.epam.esm.util;

import com.epam.esm.service.dto.CustomPage;
import com.epam.esm.service.dto.PageSetup;
import org.springframework.stereotype.Component;

@Component
public class PageCalculator {
    public Integer calculator(Integer pageNumber, Integer rowsOnPage) {
        return pageNumber * rowsOnPage;
    }

    public void calculator(CustomPage customPage, PageSetup pageSetup, Long rowsInDataBase) {
        Long totalPages = rowsInDataBase / pageSetup.getSize();
        totalPages = rowsInDataBase % pageSetup.getSize() > 0 ? totalPages + 1 : totalPages;
        customPage.setTotalElements(rowsInDataBase);
        customPage.setSize(pageSetup.getSize());
        customPage.setNumber(pageSetup.getPage() + 1);
        customPage.setTotalPages(totalPages);
    }
}
