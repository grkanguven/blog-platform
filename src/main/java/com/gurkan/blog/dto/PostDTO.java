package com.gurkan.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostDTO {
    private Long id;
    @NotBlank(message = "Başlık boş olamaz")
    @Size(min = 3, max = 100, message = "Başlık 3 ile 100 karakter arasında olmalıdır")
    private String title;
    @NotBlank(message = "İçerik boş olamaz")
    private String content;
   // @JsonIgnore  görünür olmasın
    private String author;

    // Default constructor şimdi gerek duymadık
    //public PostDTO() {
    //}

    // Parametreli constructor
    public PostDTO(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // Getter Setter lar
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
