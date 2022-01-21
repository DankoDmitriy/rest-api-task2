package com.epam.esm.contollers;

import com.epam.esm.hateaos.HateoasBuilder;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.CustomPageDto;
import com.epam.esm.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.spel.ast.OpEQ;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;
    private final HateoasBuilder hateoasBuilder;

    @Autowired
    public UserController(UserService userService, HateoasBuilder hateaosBuilder) {
        this.userService = userService;
        this.hateoasBuilder = hateaosBuilder;
    }

    @GetMapping
    public ResponseEntity<CustomPageDto> getAllUsers(Pageable pageable) {
        CustomPageDto<UserDto> customPage = userService.findAllUsersPage(pageable);
        List<UserDto> users = customPage.getItems();
        hateoasBuilder.setLinksUsers(users);
        return new ResponseEntity<>(customPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("authentication.name eq T(String).valueOf(#id)")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal();
        UserDto user = userService.findById(id);
        hateoasBuilder.setLinks(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto user) {
        UserDto userFromDataBase = userService.save(user);
        hateoasBuilder.setLinks(userFromDataBase);
        return new ResponseEntity<>(userFromDataBase, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
