package com.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {

    private List<PostDto> data;
    private int pageNumber;
    private int pageSize;
    private long totalCount;
    private int totalPages;
    private boolean isLastPage;
}
