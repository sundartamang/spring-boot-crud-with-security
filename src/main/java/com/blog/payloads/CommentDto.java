package com.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
    private Integer commentId;
    @NotBlank(message = "Content can not be blank")
    private String content;
}
