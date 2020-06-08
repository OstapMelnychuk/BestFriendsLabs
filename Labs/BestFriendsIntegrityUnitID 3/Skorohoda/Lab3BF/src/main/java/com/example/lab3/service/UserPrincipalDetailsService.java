package com.example.lab3.service;

import com.example.lab3.entity.User;
import com.example.lab3.exception.NotFoundException;
import com.example.lab3.model.UserPrincipal;
import com.example.lab3.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s).orElseThrow(() ->
                new NotFoundException("No such user with email: " + s));

        return new UserPrincipal(user);
    }
}
