package com.blog.payloads;

import com.blog.entities.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
    private Integer commentId;
    @NotBlank(message = "Content can not be blank")
    private String content;
}
