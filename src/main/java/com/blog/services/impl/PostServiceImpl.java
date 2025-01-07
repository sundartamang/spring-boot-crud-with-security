package com.blog.services.impl;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.Users;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        Users user = this.userRepo.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User ", " id", userId));

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(
                ()-> new ResourceNotFoundException("Category ", " id", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUsers(user);
        post.setCategory(category);

        Post addedPost = this.postRepo.save(post);
        return this.modelMapper.map(addedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", " id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPostDto = this.postRepo.save(post);
        return this.modelMapper.map(updatedPostDto, PostDto.class);
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> posts =  this.postRepo.findAll();
        List<PostDto> postDtos = posts.stream().map(
                (post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", " id ", postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", " id", postId));
        this.postRepo.delete(post);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category =  this.categoryRepo.findById(categoryId).orElseThrow(
                ()-> new ResourceNotFoundException("Category", " id", categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);
        List<PostDto> postDtos =  posts.stream().map(
               (post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        Users user =  this.userRepo.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User", " id", userId));
        List<Post> posts = this.postRepo.findByUsers(user);
        List<PostDto> postDtos =  posts.stream().map(
                (post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        return null;
    }
}
