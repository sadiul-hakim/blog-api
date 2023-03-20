package com.blog.services.impl;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDTO;
import com.blog.repositories.UserRepo;
import com.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepository;
    @Autowired
    public UserServiceImpl(UserRepo userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDTO createUser(UserDTO dto) {
        User user=dtoToUser(dto);
        User savedUser = this.userRepository.save(user);
        return userToDTO(savedUser);
    }
    @Override
    public UserDTO updateUser(UserDTO dto, Integer userId) {
        User user=this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setAbout(dto.getAbout());
        User savedUser = this.userRepository.save(user);
        return userToDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user=this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        return userToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> allUser = this.userRepository.findAll();
        List<UserDTO> allDTOs=new ArrayList<>();
        for(User user:allUser){
            allDTOs.add(this.userToDTO(user));
        }
        return allDTOs;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        this.userRepository.delete(user);
    }

    public User dtoToUser(UserDTO dto){
        User user= new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setAbout(dto.getAbout());
        return user;
    }

    public UserDTO userToDTO(User user){
        UserDTO dto=new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setAbout(user.getAbout());
        return dto;
    }
}
