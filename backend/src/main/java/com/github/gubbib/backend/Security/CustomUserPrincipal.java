package com.github.gubbib.backend.Security;

import com.github.gubbib.backend.Domain.User.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class CustomUserPrincipal implements UserDetails, OAuth2User {

    private final User user;
    private final List<GrantedAuthority> authorities;
    private final Map<String, Object> oauth2Attributes;

    public CustomUserPrincipal(User user) {
        this.user = user;
        this.authorities = List.of(new SimpleGrantedAuthority(user.getRole().getRoleName()));
        this.oauth2Attributes = Collections.emptyMap();
    }

    public CustomUserPrincipal(User user, Map<String, Object> oauth2Attributes) {
        this.user = user;
        this.authorities = List.of(new SimpleGrantedAuthority(user.getRole().getRoleName()));
        this.oauth2Attributes = oauth2Attributes;
    }

    @Override
    public String getName() {
        return user.getEmail();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2Attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
