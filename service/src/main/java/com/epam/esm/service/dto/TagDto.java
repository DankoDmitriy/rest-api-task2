package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto {

    private static final int ID_MIN_SIZE = 1;
    private static final String TAG_NAME_SYMBOL_REGEXP = "^[a-zA-ZА-Яа-я0-9\\s]{2,255}$";

    @Size(min = ID_MIN_SIZE, message = "{id.size}")
    private Long id;

    @Pattern(regexp = TAG_NAME_SYMBOL_REGEXP, message = "{tag.name.properties}")
    private String name;
}
