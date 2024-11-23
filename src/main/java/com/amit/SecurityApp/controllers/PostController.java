package com.amit.SecurityApp.controllers;


import com.amit.SecurityApp.dto.PostDto;
import com.amit.SecurityApp.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }


    @GetMapping("/{postId}")
    @PreAuthorize("@postSecurity.isOwnerOfPost(#postId)")
    public PostDto getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    @PostMapping
    public PostDto createNewPost(@RequestBody PostDto postDto){
        return postService.createNewPost(postDto);
    }
}
