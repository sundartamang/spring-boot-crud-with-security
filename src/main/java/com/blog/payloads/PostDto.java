package com.blog.payloads;

import com.blog.entities.Category;
import com.blog.entities.Comment;
import com.blog.entities.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private Integer postId;
    @NotBlank(message = "Title can not be blank")
    private String title;
    private String content;
    private String imageName;
    private CategoryDto category;
    private UserDto users;

    private Set<CommentDto> comments = new HashSet<>();
}
