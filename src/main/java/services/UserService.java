package services;

import payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUSer(UserDto user);
    UserDto updateUser(UserDto user, Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);
}
