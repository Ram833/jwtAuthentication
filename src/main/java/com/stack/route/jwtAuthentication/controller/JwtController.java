package com.stack.route.jwtAuthentication.controller;

import com.stack.route.jwtAuthentication.model.Username;
import com.stack.route.jwtAuthentication.service.CustomUserDetailsService;
import com.stack.route.jwtAuthentication.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping("/autherization")
    public ResponseEntity<?> genarateToken(@RequestBody Username username){

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username.getUsername(),
                    username.getPassword()));

        }catch (UsernameNotFoundException e){
            e.printStackTrace();
        }
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        System.out.println("the token is"+token);

        return ResponseEntity.ok(token);

    }
}
