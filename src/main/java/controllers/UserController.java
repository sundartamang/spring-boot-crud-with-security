package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payloads.UserDto;
import services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto createdUserDto = this.userService.createUSer(userDto);
        return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
    }
}
