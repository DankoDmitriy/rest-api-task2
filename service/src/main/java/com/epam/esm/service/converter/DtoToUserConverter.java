package com.epam.esm.service.converter;

import com.epam.esm.model.impl.User;
import com.epam.esm.service.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToUserConverter implements Converter<UserDto, User> {
    @Override
    public User convert(UserDto source) {
        return User.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}
