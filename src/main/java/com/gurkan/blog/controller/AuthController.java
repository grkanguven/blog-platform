package com.gurkan.blog.controller;

import com.gurkan.blog.config.JwtUtil;
import com.gurkan.blog.entity.User;
import com.gurkan.blog.service.CustomUserDetailsService;
import com.gurkan.blog.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                          CustomUserDetailsService userDetailsService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    // REGISTER FORM GÖSTERİMİ
    @GetMapping("/register")
    public String registerForm() {
        return "register";  // templates/register.html
    }

    // REGISTER FORM SUBMIT
    @PostMapping("/register")
    public String registerSubmit(@RequestParam String username,
                                 @RequestParam String password,
                                 Model model) {
        try {
            userService.registerUser(username, password, "ROLE_USER");
            model.addAttribute("success", "Kullanıcı başarıyla oluşturuldu!");
            return "register";
        } catch (Exception e) {
            model.addAttribute("error", "Kayıt hatası: " + e.getMessage());
            return "register";
        }
    }

    // FORM LOGIN 
    @GetMapping("/login")
    public String loginForm() {
        return "login"; // templates/login.html
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username,
                              @RequestParam String password,
                              HttpServletResponse response,
                              Model model) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String token = jwtUtil.generateToken(userDetails.getUsername());

            Cookie cookie = new Cookie("jwtToken", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            return "redirect:/posts";
        } catch (AuthenticationException e) {
            model.addAttribute("error", "Kullanıcı adı veya şifre hatalı");
            return "login";
        }
    }

    // JSON API LOGIN
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> loginJson(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            String token = jwtUtil.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Geçersiz kullanıcı adı veya parola");
        }
    }

    // JSON API REGISTER 
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json", params = "!username")
    @ResponseBody
    public ResponseEntity<?> registerJson(@RequestBody RegisterRequest request) {
        try {
            User newUser = userService.registerUser(request.getUsername(), request.getPassword(), "ROLE_USER");
            return ResponseEntity.ok("Kullanıcı oluşturuldu: " + newUser.getUsername());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Kullanıcı oluşturulurken hata: " + e.getMessage());
        }
    }

    // JSON için DTO larımız
    public static class AuthRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class AuthResponse {
        private final String token;

        public AuthResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }

    public static class RegisterRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
