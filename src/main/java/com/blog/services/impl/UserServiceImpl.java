package com.blog.services.impl;

import com.blog.entities.Users;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blog.repositories.UserRepo;
import com.blog.services.UserService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUSer(UserDto userDto) {
        Users user = this.dtoToUser(userDto);
        Users savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        Users user = this.userRepo.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User", " id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(user.getAbout());
        Users updatedUser = this.userRepo.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        Users user = this.userRepo.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User ", " id", userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<Users> users = this.userRepo.findAll();
       List<UserDto> userDtos = users.stream().map(
               user -> this.userToDto(user)).collect(Collectors.toList());
       return  userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        Users user = this.userRepo.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User ",  " Id", userId));
        System.out.println("User to be deleted "+ user);
        this.userRepo.delete(user);
    }

    private Users dtoToUser(UserDto userDto){
        Users user = this.modelMapper.map(userDto, Users.class);
        return user;
    }

    private UserDto userToDto(Users user){
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return  userDto;
    }
}
