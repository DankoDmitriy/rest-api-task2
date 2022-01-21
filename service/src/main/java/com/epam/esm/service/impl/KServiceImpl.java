package com.epam.esm.service.impl;

import com.epam.esm.constant.ErrorMessagesConstant;
import com.epam.esm.exception.LoginException;
import com.epam.esm.exception.RegistrationException;
import com.epam.esm.model.impl.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.KService;
import com.epam.esm.service.dto.AuthDto;
import com.epam.esm.service.dto.RegistrationUserDto;
import com.epam.esm.service.dto.TokenDto;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class KServiceImpl implements KService {
    private final static String USER_ID_ATTRIBUTE = "giftId";

    @Value("${keycloak.url}")
    private String keycloakUrl;

    @Value("${keycloak.realm.client.name}")
    private String keycloakClientName;

    @Value("${keycloak.realm.secret}")
    private String keycloakRealmSecret;

    @Value("${keycloak.realm.name}")
    private String keycloakRealmName;

    private final Keycloak keycloak;
    private final UserRepository userRepository;

    @Autowired
    public KServiceImpl(Keycloak keycloak, UserRepository userRepository) {
        this.keycloak = keycloak;
        this.userRepository = userRepository;
    }

    @Override
    public TokenDto login(AuthDto authDto) {
        TokenManager tokenManager = getTokenManager(authDto);
        String tokenStr = null;
        try {
            tokenStr = tokenManager.getAccessTokenString();
        } catch (BadRequestException | NotAuthorizedException exception) {
            throw new LoginException(ErrorMessagesConstant.USER_IS_NOT_FOUND, authDto.getUserName());
        }

        Optional<User> optionalUser = userRepository.findByName(authDto.getUserName());
        if (!optionalUser.isPresent()) {
            User saveUser = userRepository.save(User.builder()
                    .name(authDto.getUserName())
                    .build());

            RealmResource realm = keycloak.realm(keycloakRealmName);
            UsersResource usersResource = realm.users();
            List<UserRepresentation> representations = usersResource.search(authDto.getUserName());
            if (!representations.isEmpty()) {
                UserRepresentation userRepresentation = representations.get(0);
                userRepresentation.singleAttribute(USER_ID_ATTRIBUTE, saveUser.getId().toString());
                UserResource userResource = usersResource.get(userRepresentation.getId());
                userResource.update(userRepresentation);
            }
        }
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(tokenStr);
        return tokenDto;
    }

    @Override
    public void registration(RegistrationUserDto registrationUserDto) {
        Optional<User> optionalUser = userRepository.findByName(registrationUserDto.getUserName());
        if (!optionalUser.isPresent()) {
            User saveUser = userRepository.save(User.builder().name(registrationUserDto.getUserName()).build());
            UserRepresentation user = createUser(
                    registrationUserDto.getUserName(),
                    registrationUserDto.getEmail(),
                    registrationUserDto.getPassword(), saveUser.getId().toString());

            RealmResource realm = keycloak.realm(keycloakRealmName);
            UsersResource users = realm.users();
            users.create(user);
        } else {
            throw new RegistrationException(ErrorMessagesConstant.USER_WITH_THIS_NAME_EXISTS, registrationUserDto.getUserName());
        }
    }

    private UserRepresentation createUser(String username, String email, String password, String userId) {
        UserRepresentation user = new UserRepresentation();
        user.singleAttribute(USER_ID_ATTRIBUTE, userId);
        user.setUsername(username);
        user.setEmail(email);
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setCredentials(Collections.singletonList(createCredential(password)));
        return user;
    }

    private CredentialRepresentation createCredential(String cred) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(cred);
        credentialRepresentation.setTemporary(false);
        return credentialRepresentation;
    }

    private TokenManager getTokenManager(AuthDto authDto) {
        return Keycloak.getInstance(
                        keycloakUrl,
                        keycloakRealmName,
                        authDto.getUserName(),
                        authDto.getPassword(),
                        keycloakClientName,
                        keycloakRealmSecret)
                .tokenManager();
    }
}
