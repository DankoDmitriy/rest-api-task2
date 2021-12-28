package com.epam.esm.model.impl;

import com.epam.esm.model.AbstractEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CustomPage<T extends AbstractEntity> implements Serializable, Cloneable {
    private Integer size;
    private Long totalElements;
    private Long totalPages;
    private Integer number;
    private List<T> items;
}
