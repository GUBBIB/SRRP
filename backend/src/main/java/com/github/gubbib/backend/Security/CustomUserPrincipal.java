package com.github.gubbib.backend.Security;

import com.github.gubbib.backend.Domain.User.User;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Getter
public class CustomUserPrincipal implements UserDetails, OAuth2User {

    private final User user;
    private final List<GrantedAuthority> authorities;
    private final Map<String, Object> oauth2Attributes;
    private final boolean newUser;

    public CustomUserPrincipal(User user) {
        this.user = user;
        this.authorities = List.of(new SimpleGrantedAuthority(user.getRole().getRoleName()));
        this.newUser = false;
        this.oauth2Attributes = Collections.emptyMap();
    }

    public CustomUserPrincipal(User user, Map<String, Object> oauth2Attributes, boolean newUser) {
        this.user = user;

        if (user != null && user.getRole() != null) {
            this.authorities = List.of(new SimpleGrantedAuthority(user.getRole().getRoleName()));
        } else {
            this.authorities = List.of(new SimpleGrantedAuthority("ROLE_GUEST"));
        }

        this.newUser = newUser;
        this.oauth2Attributes = oauth2Attributes;
    }

    @Override
    public String getName() { // Oauth2User
        if(user != null) return user.getName();
        Object emailAttr =  oauth2Attributes.get("email");
        return  emailAttr != null ? emailAttr.toString() : "";
    }

    @Override
    public Map<String, Object> getAttributes() { // Oauth2User
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
