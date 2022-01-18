package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomPageDto<T extends AbstractDto> implements Serializable, Cloneable {
    private Integer size;
    private Long totalElements;
    private int totalPages;
    private int number;
    private List<T> items;
}
