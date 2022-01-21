package com.epam.esm.service.converter;

import com.epam.esm.model.impl.User;
import com.epam.esm.service.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToDtoConverter implements Converter<User, UserDto> {
    @Override
    public UserDto convert(User source) {
        return UserDto.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}
