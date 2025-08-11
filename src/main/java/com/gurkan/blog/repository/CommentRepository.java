package com.gurkan.blog.repository;

import com.gurkan.blog.entity.Comment;
import com.gurkan.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findByPost(Post post);    //yorumlarımızı getirecez burda
}
