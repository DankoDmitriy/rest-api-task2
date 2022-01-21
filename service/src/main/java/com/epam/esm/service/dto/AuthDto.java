package com.epam.esm.service.dto;

import lombok.Data;

@Data
public class AuthDto implements AbstractDto {
    private String userName;
    private String password;
}
