package com.example.lab3.model;

import com.example.lab3.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class UserPrincipal  implements UserDetails {
    private User user;

    public UserPrincipal(User user){
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        this.user.getPermissionList().forEach(p -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(p);
            grantedAuthorities.add(grantedAuthority);
        });
        this.user.getRolesList().forEach(r -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" +  r);
            grantedAuthorities.add(grantedAuthority);
        });

        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
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
        return this.user.isActive();
    }
}
