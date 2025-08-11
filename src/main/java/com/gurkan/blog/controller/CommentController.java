package com.gurkan.blog.controller;

import com.gurkan.blog.entity.Comment;
import com.gurkan.blog.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@PathVariable Long postId,
                                              @RequestBody Comment comment) {
        Comment newComment = commentService.addComment(postId, comment.getUsername_comment(), comment.getContent());
        return ResponseEntity.ok(newComment);
    }
}
