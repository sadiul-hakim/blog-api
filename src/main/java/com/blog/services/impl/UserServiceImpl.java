package com.blog.services.impl;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDTO;
import com.blog.repositories.UserRepo;
import com.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
                .orElseThrow(()->new ResourceNotFoundException("User","id",userId.toString()));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setAbout(dto.getAbout());
        User savedUser = this.userRepository.save(user);
        return userToDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user=this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","id",userId.toString()));
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
                .orElseThrow(()->new ResourceNotFoundException("User","id",userId.toString()));
        this.userRepository.delete(user);
    }

    public User dtoToUser(UserDTO dto){
        return modelMapper.map(dto,User.class);
    }

    public UserDTO userToDTO(User user){
       return modelMapper.map(user,UserDTO.class);
    }
}
