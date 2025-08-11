package com.gurkan.blog.controller;

import com.gurkan.blog.dto.PostDTO;
import com.gurkan.blog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PostPageController {

    private final PostService postService;

    public PostPageController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String postsPage(Model model) {
        List<PostDTO> posts = postService.findAllDto();
        System.out.println("Post sayısı: " + posts.size());
        model.addAttribute("posts", posts);
        return "posts"; // templates/posts.html
    }
}
