package com.blog.controllers;

import com.blog.config.AppConstants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @Valid @ModelAttribute PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        String fileName = this.fileService.uploadImage(AppConstants.IMAGE_PATH, image);
        postDto.setImageName(fileName);
        PostDto newPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(newPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
        List<PostDto> postsByUser = this.postService.getPostByUser(userId);
        return new ResponseEntity<List<PostDto>>(postsByUser, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
        List<PostDto> postsByCategory = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(postsByCategory, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SOR_DIR, required = false) String sortDir) {
        PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getAllPost(@PathVariable Integer postId) {
        PostDto postDto = this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(
            @Valid @ModelAttribute PostDto postDto,
            @PathVariable Integer postId,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        PostDto existingPost = this.postService.getPostById(postId);
        // Delete the old image if it exists
        if (existingPost.getImageName() != null) {
            this.fileService.deleteFile(AppConstants.IMAGE_PATH, existingPost.getImageName());
        }
        // Upload the new image
        String fileName = this.fileService.uploadImage(AppConstants.IMAGE_PATH, image);
        postDto.setImageName(fileName);

        PostDto updatedPost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) throws IOException {
        PostDto postDto = this.postService.getPostById(postId);

        // Delete the image associated with the post
        if (postDto.getImageName() != null) {
            this.fileService.deleteFile(AppConstants.IMAGE_PATH, postDto.getImageName());
        }

        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchByTitle(
            @PathVariable("keywords") String keywords) {
        List<PostDto> filterPosts = this.postService.searchPosts(keywords);
        return new ResponseEntity<List<PostDto>>(filterPosts, HttpStatus.OK);
    }
}
