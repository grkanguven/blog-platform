package com.gurkan.blog.controller;

import com.gurkan.blog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostPageController {

    private final PostService postService;

    public PostPageController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String postsPage(Model model) {
        var posts = postService.findAll();
        System.out.println("Post sayısı: " + posts.size());  // Konsola kaç post geldi bak
        model.addAttribute("posts", posts);
        return "posts"; // templates/posts.html dosyasını döner
    }
}
