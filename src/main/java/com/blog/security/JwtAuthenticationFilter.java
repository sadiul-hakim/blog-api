package com.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final UserDetailsService userDetailsService;
    private final JwtTokenHelper tokenHelper;
    @Autowired
    public JwtAuthenticationFilter(UserDetailsService userDetailsService, JwtTokenHelper tokenHelper) {
        this.userDetailsService = userDetailsService;
        this.tokenHelper = tokenHelper;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestToken = request.getHeader("Authorization");
        //get username and token
        String username=null;
        String token=null;
        if(requestToken!=null && requestToken.startsWith("Bearer ")){
            token = requestToken.substring(7);
            username=tokenHelper.extractUsername(token);
        }else{
            System.out.println("Token is Either null or does not start with Bearer.");
        }
        //validate token
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(tokenHelper.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }else{
                System.out.println("Invalid Token.");
            }
        }else{
            System.out.println("username is null or context is not null.");
        }
        filterChain.doFilter(request,response);
    }
}
