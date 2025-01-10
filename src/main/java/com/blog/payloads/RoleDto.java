package com.blog.payloads;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class RoleDto {
    @Id
    private Integer id;
    private String name;
}
