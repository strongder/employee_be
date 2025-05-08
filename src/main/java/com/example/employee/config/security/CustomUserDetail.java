package com.example.employee.config.security;

import com.example.employee.model.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class CustomUserDetail implements UserDetails {

    private final String email;
    private final String password;
    private final Set<GrantedAuthority> authorities; //ROLE_ADMIN


    public CustomUserDetail(Account account) {
        this.email = account.getUsername();
        this.password = account.getPasswordHash();
        this.authorities = Set.of(new SimpleGrantedAuthority("ROLE_" + account.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
