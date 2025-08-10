package com.gurkan.blog.mapper;

import com.gurkan.blog.dto.PostDTO;
import com.gurkan.blog.entity.Post;

public class PostMapper {

    public static PostDTO toDTO(Post post) {
        return new PostDTO(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getAuthor()
        );
    }

    public static Post toEntity(PostDTO dto) {
        Post post = new Post();
        post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setAuthor(dto.getAuthor());
        return post;
    }
}
