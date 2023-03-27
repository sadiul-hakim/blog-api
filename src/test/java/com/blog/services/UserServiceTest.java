package com.blog.services;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDTO;
import com.blog.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepo userRepo;

    @BeforeEach
    void setup(){
        User user=new User();
        user.setId(3);

        Mockito.when(userRepo.findById(3)).thenReturn(Optional.of(user));
    }

    @Test
    public void whenValidUserId_ThenUserShouldBeFound(){
        Integer userId=3;
        UserDTO userById = userService.getUserById(userId);
        assertEquals(userId,userById.getId());
    }

    @Test
    public void whenInvalidUserId_ThenUserShouldNotBeFound(){
        Integer userId=1;
        try{
            UserDTO userById = userService.getUserById(userId);
        }catch (ResourceNotFoundException ex){
            assertEquals(ResourceNotFoundException.class,ex.getClass());
        }
    }
}