package com.blog.services;

import com.blog.entities.Post;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
    PostDto updatePost(PostDto postDto, Integer postId);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize);
    PostDto getPostById(Integer postId);
    void deletePost(Integer postId);

    // get all posts by category
    List<PostDto> getPostByCategory(Integer categoryId);

    // get all posts by user
    List<PostDto> getPostByUser(Integer userId);

    // search post by keyword
    List<PostDto> searchPosts(String keyword);

}
