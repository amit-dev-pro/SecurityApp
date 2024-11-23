package com.amit.SecurityApp.services;

import com.amit.SecurityApp.dto.PostDto;
import com.amit.SecurityApp.entities.PostEntity;
import com.amit.SecurityApp.entities.User;
import com.amit.SecurityApp.exception.ResourceNotFoundException;
import com.amit.SecurityApp.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity,PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto createNewPost(PostDto postDto) {
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostEntity postEntity=modelMapper.map(postDto,PostEntity.class);
        postEntity.setAuthor(user);
        return modelMapper.map(postRepository.save(postEntity),PostDto.class);
    }

    @Override
    public PostDto getPostById(Long postId) {
        PostEntity postEntity = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id "+postId));
        return modelMapper.map(postEntity, PostDto.class);
    }
}
