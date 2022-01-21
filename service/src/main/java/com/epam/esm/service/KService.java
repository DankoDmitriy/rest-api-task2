package com.epam.esm.service;

import com.epam.esm.service.dto.AuthDto;
import com.epam.esm.service.dto.RegistrationUserDto;
import com.epam.esm.service.dto.TokenDto;

public interface KService {
    TokenDto login(AuthDto authDto);

    void registration(RegistrationUserDto registrationUserDto);
}
