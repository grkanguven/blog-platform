package com.gurkan.blog.controller;

import com.gurkan.blog.dto.PostDTO;
import com.gurkan.blog.entity.Post;
import com.gurkan.blog.mapper.PostMapper;
import com.gurkan.blog.service.PostService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    
    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.findAll().stream()
            .map(PostMapper::toDTO)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return postService.findById(id)
            .map(PostMapper::toDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PostDTO createPost(@RequestBody @Valid PostDTO postDTO) {
        Post saved = postService.save(PostMapper.toEntity(postDTO));
        return PostMapper.toDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody @Valid PostDTO updatedDTO) {
        return postService.findById(id)
            .map(post -> {
                post.setTitle(updatedDTO.getTitle());
                post.setContent(updatedDTO.getContent());
                post.setAuthor(updatedDTO.getAuthor());
                Post updated = postService.save(post);
                return ResponseEntity.ok(PostMapper.toDTO(updated));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (postService.findById(id).isPresent()) {
            postService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
