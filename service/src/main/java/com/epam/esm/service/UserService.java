package com.epam.esm.service;

import com.epam.esm.model.impl.User;
import com.epam.esm.service.dto.PageSetup;

import java.util.List;

public interface UserService extends BaseService<User> {
    List<User> findAll(PageSetup pageSetup);
}
