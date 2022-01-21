package com.epam.esm.contollers;

import com.epam.esm.service.KService;
import com.epam.esm.service.dto.AuthDto;
import com.epam.esm.service.dto.RegistrationUserDto;
import com.epam.esm.service.dto.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private final KService kService;

    @Autowired
    public AuthenticationController(KService kService) {
        this.kService = kService;
    }

    @PostMapping("/singup")
    public ResponseEntity<Void> registration(@RequestBody RegistrationUserDto registrationUserDto) {
        kService.registration(registrationUserDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthDto authDto) {
        TokenDto tokenDto = kService.login(authDto);
        return new ResponseEntity<>(tokenDto, HttpStatus.CREATED);
    }
}
