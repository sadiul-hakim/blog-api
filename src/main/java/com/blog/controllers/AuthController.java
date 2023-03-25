package com.blog.controllers;

import com.blog.payloads.JwtAuthRequest;
import com.blog.payloads.JwtAuthResponse;
import com.blog.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final JwtTokenHelper tokenHelper;
    private final UserDetailsService userDetailService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(
            JwtTokenHelper tokenHelper,
            UserDetailsService userDetailService,
            AuthenticationManager authenticationManager) {
        this.tokenHelper = tokenHelper;
        this.userDetailService = userDetailService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest){
        authenticate(jwtAuthRequest.getUsername(),jwtAuthRequest.getPassword());
        UserDetails userDetails=userDetailService.loadUserByUsername(jwtAuthRequest.getUsername());
        String token = tokenHelper.generateToken(userDetails);
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }
    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(username,password);
        authenticationManager.authenticate(authToken);
    }
}
