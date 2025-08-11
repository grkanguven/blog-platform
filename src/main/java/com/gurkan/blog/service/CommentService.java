package com.gurkan.blog.service;

import com.gurkan.blog.entity.Comment;
import com.gurkan.blog.entity.Post;
import com.gurkan.blog.repository.CommentRepository;
import com.gurkan.blog.repository.PostRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

                // post yorum getir
    public List<Comment> getCommentsByPostId(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        return post.map(commentRepository::findByPost).orElse(List.of());
    }

             // Yeni yorum ekle
    public Comment addComment(Long postId, String username, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post bulunamadı, id: " + postId));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUsername_comment(username);
        comment.setContent(content);
                                                    // createdAt otomatik set

        return commentRepository.save(comment);
    }
}
