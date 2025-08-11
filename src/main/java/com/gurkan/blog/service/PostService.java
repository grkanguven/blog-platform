package com.gurkan.blog.service;

import com.gurkan.blog.dto.PostDTO;
import com.gurkan.blog.entity.Post;
import com.gurkan.blog.mapper.PostMapper;
import com.gurkan.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostDTO> findAllDto() {
        return postRepository.findAll()
                .stream()
                .map(PostMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PostDTO> findByIdDto(Long id) {
        return postRepository.findById(id)
                .map(PostMapper::toDTO);
    }

    public Post saveDto(PostDTO dto) {
        Post post = PostMapper.toEntity(dto);
        return postRepository.save(post);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}
