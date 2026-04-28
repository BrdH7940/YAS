package com.yas.backofficebff.controller;

import com.yas.backofficebff.viewmodel.AuthenticatedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticationControllerTest {

    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        authenticationController = new AuthenticationController();
    }

    @Test
    void user_WhenPrincipalIsNotNull_ShouldReturnAuthenticatedUser() {
        OAuth2User principal = mock(OAuth2User.class);
        when(principal.getAttribute("preferred_username")).thenReturn("backofficetestuser");

        ResponseEntity<AuthenticatedUser> response = authenticationController.user(principal);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("backofficetestuser", response.getBody().username());
    }
}

