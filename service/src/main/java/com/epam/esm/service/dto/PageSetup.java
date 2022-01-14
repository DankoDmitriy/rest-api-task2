package com.epam.esm.service.dto;

import lombok.Data;

@Data
public class PageSetup {
    private static final Integer DEFAULT_PAGE = 0;
    private static final Integer DEFAULT_SIZE = 1;

    private Integer page = DEFAULT_PAGE;
    private Integer size = DEFAULT_SIZE;

    /**
     * The requested page is decremented by -1 because the user uses pagination starting from 1 and the system from 0.
     * @param page
     */
    public void setPage(Integer page) {
        this.page = page != null ? page - 1 : DEFAULT_PAGE;
    }

    public void setSize(Integer size) {
        this.size = size != null ? size : DEFAULT_SIZE;
    }
}
