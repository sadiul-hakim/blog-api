package com.blog.controllers;

import com.blog.payloads.JwtAuthRequest;
import com.blog.payloads.JwtAuthResponse;
import com.blog.payloads.UserDTO;
import com.blog.security.JwtTokenHelper;
import com.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final JwtTokenHelper tokenHelper;
    private final UserDetailsService userDetailService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public AuthController(
            JwtTokenHelper tokenHelper,
            UserDetailsService userDetailService,
            AuthenticationManager authenticationManager,
            UserService userService) {
        this.tokenHelper = tokenHelper;
        this.userDetailService = userDetailService;
        this.authenticationManager = authenticationManager;
        this.userService=userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest){
        authenticate(jwtAuthRequest.getUsername(),jwtAuthRequest.getPassword());
        UserDetails userDetails=userDetailService.loadUserByUsername(jwtAuthRequest.getUsername());
        String token = tokenHelper.generateToken(userDetails);
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO dto){
        UserDTO savedDTO = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(username,password);
        authenticationManager.authenticate(authToken);
    }
}
