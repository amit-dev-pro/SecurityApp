package com.amit.SecurityApp.services;

import com.amit.SecurityApp.dto.PostDto;

import java.util.List;

public interface  PostService {

    List<PostDto> getAllPosts();
    PostDto createNewPost(PostDto postDto);
    PostDto getPostById(Long postId);

}
