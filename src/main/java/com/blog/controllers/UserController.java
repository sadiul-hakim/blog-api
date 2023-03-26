package com.blog.controllers;


import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDTO;
import com.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO dto){
        UserDTO savedDTO = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(
            @Valid @RequestBody UserDTO userDTO,
            @PathVariable("userId") Integer userId
    ){
            UserDTO dto = userService.updateUser(userDTO, userId);
            return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId){
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User deleted successfully",true));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Integer userId){
            UserDTO userDTO = userService.getUserById(userId);
            return ResponseEntity.ok(userDTO);
    }
}
