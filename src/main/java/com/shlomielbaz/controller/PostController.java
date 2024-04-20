package com.shlomielbaz.controller;


import com.shlomielbaz.entity.Post;
import com.shlomielbaz.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // build create Post REST API
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        Post savedPost = (Post) postService.create(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    // build get post by id REST API
    // http://localhost:8080/api/posts/1
    @GetMapping("{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Long postId){
        Post post = (Post) postService.getById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // Build Get All Posts REST API
    // http://localhost:8080/api/posts
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts = postService.getAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // Build Update Post REST API
    @PutMapping("{id}")
    // http://localhost:8080/api/posts/1
    public ResponseEntity<Post> updatePost(@PathVariable("id") Long postId,
                                           @RequestBody Post post){
        post.setId(postId);
        Post updatedPost = (Post) postService.update(post);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    // Build Delete Post REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long postId){
        postService.delete(postId);
        return new ResponseEntity<>("Post successfully deleted!", HttpStatus.OK);
    }
}
