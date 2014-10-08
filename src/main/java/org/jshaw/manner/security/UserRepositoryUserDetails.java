package org.jshaw.manner.security;

import org.jshaw.manner.domain.Role;
import org.jshaw.manner.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserRepositoryUserDetails extends User implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authList = new ArrayList<>();
        if (super.getRole().equals(Role.SUPER_ADMIN)) {
            authList.add(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"));
            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if (super.getRole().equals(Role.ADMIN)) {
            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if (super.getRole().equals(Role.USER)) {
            authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authList;
    }

    @Override
    public String getUsername() {
        return super.getUsername();
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
