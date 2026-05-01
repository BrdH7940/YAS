package com.yas.promotion.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

class AuthenticationUtilsTest {

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void extractUserId_whenJwtAuthentication_returnsSubject() {
        Jwt jwt = mock(Jwt.class);
        when(jwt.getSubject()).thenReturn("user-123");
        when(jwt.getTokenValue()).thenReturn("token-value");

        JwtAuthenticationToken jwtAuth = new JwtAuthenticationToken(jwt);
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(jwtAuth);
        SecurityContextHolder.setContext(context);

        String result = AuthenticationUtils.extractUserId();

        assertThat(result).isEqualTo("user-123");
    }

    @Test
    void extractUserId_whenAnonymousAuthentication_throwsAccessDeniedException() {
        AnonymousAuthenticationToken anonymousToken = new AnonymousAuthenticationToken(
            "key", "anonymous", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(anonymousToken);
        SecurityContextHolder.setContext(context);

        assertThatThrownBy(AuthenticationUtils::extractUserId)
            .isInstanceOf(AccessDeniedException.class)
            .hasMessage(Constants.ErrorCode.ACCESS_DENIED);
    }

    @Test
    void extractJwt_whenJwtAuthentication_returnsTokenValue() {
        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn("my-jwt-token");

        org.springframework.security.core.Authentication auth = mock(org.springframework.security.core.Authentication.class);
        when(auth.getPrincipal()).thenReturn(jwt);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        String result = AuthenticationUtils.extractJwt();

        assertThat(result).isEqualTo("my-jwt-token");
    }
}
