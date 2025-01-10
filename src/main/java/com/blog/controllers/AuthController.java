package com.blog.controllers;

import com.blog.entities.Users;
import com.blog.exceptions.ApiException;
import com.blog.payloads.JwtAuthRequest;
import com.blog.payloads.JwtAuthResponse;
import com.blog.payloads.UserDto;
import com.blog.repositories.UserRepo;
import com.blog.security.JwtTokenHelper;
import com.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/user-login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest jwtAuthRequest) throws Exception{
        this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());
        UserDetails userDetails =  this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
        String token = this.jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        Optional<Users> user = this.userRepo.findByEmail(jwtAuthRequest.getUsername());
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        response.setUser(userDto);
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        UserDto registerNewUSer = this.userService.registerNewUSer(userDto);
        return new ResponseEntity<UserDto>(registerNewUSer, HttpStatus.CREATED);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password);
        try{
            this.authenticationManager.authenticate(auth);
        }catch (BadCredentialsException e){
            System.out.println("Invalid username and password");
            throw  new ApiException("Invalid username or password");
        }
    }


}
