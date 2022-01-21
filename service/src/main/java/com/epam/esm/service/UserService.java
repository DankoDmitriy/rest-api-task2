package com.epam.esm.service;

import com.epam.esm.service.dto.CustomPageDto;
import com.epam.esm.service.dto.UserDto;
import org.springframework.data.domain.Pageable;

/**
 * The interface User service.
 */
public interface UserService extends BaseService<UserDto> {
    /**
     * Find all custom page.
     *
     * @param pageSetup the page setup
     * @return the custom page
     */
    CustomPageDto findAllUsersPage(Pageable pageable);
}
