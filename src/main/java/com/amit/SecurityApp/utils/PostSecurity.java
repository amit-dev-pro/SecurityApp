package com.amit.SecurityApp.utils;


import com.amit.SecurityApp.dto.PostDto;
import com.amit.SecurityApp.entities.User;
import com.amit.SecurityApp.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSecurity {

    private final PostService postService;

    public boolean isOwnerOfPost(Long postId){
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDto postDto=postService.getPostById(postId);
        return postDto.getAuthor().getId().equals(user.getId());
    }

}
