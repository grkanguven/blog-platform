package com.gurkan.blog.repository;

import com.gurkan.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // gerekirse ozel sorguları buraya yazacağım
}
