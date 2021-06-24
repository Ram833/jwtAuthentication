package com.stack.route.jwtAuthentication.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        if (userName.equalsIgnoreCase("admin")){
            return new User("admin","test",new ArrayList<>());
        }else {
            throw new UsernameNotFoundException("user not found");
        }
    }
}
