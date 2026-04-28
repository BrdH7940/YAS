package com.yas.storefrontbff.controller;

import com.yas.storefrontbff.viewmodel.AuthenticatedUserVm;
import com.yas.storefrontbff.viewmodel.AuthenticationInfoVm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticationControllerTest {

    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        authenticationController = new AuthenticationController();
    }

    @Test
    void user_WhenPrincipalIsNull_ShouldReturnUnauthenticated() {
        ResponseEntity<AuthenticationInfoVm> response = authenticationController.user(null);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isAuthenticated());
    }

    @Test
    void user_WhenPrincipalIsNotNull_ShouldReturnAuthenticatedUser() {
        OAuth2User principal = mock(OAuth2User.class);
        when(principal.getAttribute("preferred_username")).thenReturn("testuser");

        ResponseEntity<AuthenticationInfoVm> response = authenticationController.user(principal);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isAuthenticated());
        AuthenticatedUserVm user = response.getBody().getAuthenticatedUser();
        assertNotNull(user);
    }
}

