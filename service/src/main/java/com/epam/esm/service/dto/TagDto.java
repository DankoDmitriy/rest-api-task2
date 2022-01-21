package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto extends RepresentationModel<TagDto> implements AbstractDto {

    private static final String TAG_NAME_SYMBOL_REGEXP = "^[a-zA-ZА-Яа-я0-9\\s]{2,255}$";

    private Long id;

    @Pattern(regexp = TAG_NAME_SYMBOL_REGEXP, message = "{tag.name.properties}")
    private String name;
}
