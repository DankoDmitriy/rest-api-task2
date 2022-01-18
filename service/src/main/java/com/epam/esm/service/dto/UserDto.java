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
public class UserDto extends RepresentationModel<UserDto> implements AbstractDto {

    private static final String USER_NAME_SYMBOL_REGEXP = "^[a-zA-Z0-9\\s]{2,45}$";

    private Long id;

    @Pattern(regexp = USER_NAME_SYMBOL_REGEXP, message = "{user.name.properties}")
    private String name;
}
