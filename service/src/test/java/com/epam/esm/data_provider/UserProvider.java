package com.epam.esm.data_provider;

import com.epam.esm.model.impl.User;

import java.util.Arrays;
import java.util.List;

public class UserProvider {
    public User getUser() {
        User user = getUserWithOutId();
        user.setId(1L);
        return user;
    }

    public User getUserWithOutId() {
        User user = new User();
        user.setName("user1");
        return user;
    }

    public List<User> getUserList() {
        return Arrays.asList(getUser());
    }
}
