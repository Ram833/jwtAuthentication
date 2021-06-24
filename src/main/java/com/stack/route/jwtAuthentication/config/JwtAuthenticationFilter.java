package com.stack.route.jwtAuthentication.config;

import com.stack.route.jwtAuthentication.service.CustomUserDetailsService;
import com.stack.route.jwtAuthentication.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestTokenHeader = request.getHeader("Authentication");
        String username = null;
        String jwtToken = null;
        
        if (requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken = requestTokenHeader.substring(7);
            
            try{
                username = this.jwtUtil.extractUsername(jwtToken);
            }catch (Exception e){
                e.printStackTrace();
            }

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
                if (jwtUtil.validateToken(jwtToken, userDetails)) {
//                    need explainnation
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
