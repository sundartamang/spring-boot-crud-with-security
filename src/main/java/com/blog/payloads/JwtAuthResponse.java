package com.blog.payloads;

import com.blog.payloads.UserDto;
import lombok.Data;

@Data
public class JwtAuthResponse {

    private String token;

    private UserDto user;
}