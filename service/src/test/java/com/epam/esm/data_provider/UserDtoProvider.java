package com.epam.esm.data_provider;

import com.epam.esm.service.dto.UserDto;

import java.util.Arrays;
import java.util.List;

public class UserDtoProvider {
    public UserDto getUser() {
        UserDto user = getUserWithOutId();
        user.setId(1L);
        return user;
    }

    public UserDto getUserWithOutId() {
        UserDto user = new UserDto();
        user.setName("user1");
        return user;
    }

    public List<UserDto> getUserList() {
        return Arrays.asList(getUser());
    }
}
