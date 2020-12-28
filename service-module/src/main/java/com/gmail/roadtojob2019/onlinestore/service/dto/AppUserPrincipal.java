package com.gmail.roadtojob2019.onlinestore.service.dto;

import com.gmail.roadtojob2019.onlinestore.repository.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AppUserPrincipal implements UserDetails {
    private User user;
    private Set<GrantedAuthority> roles = new HashSet<>();

    public AppUserPrincipal(User user) {
        this.user = user;
        roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
