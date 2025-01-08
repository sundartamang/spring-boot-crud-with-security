package com.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
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
