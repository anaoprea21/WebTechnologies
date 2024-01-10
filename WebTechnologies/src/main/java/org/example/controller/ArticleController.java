package org.example.controller;

import org.example.helper.Article;
import org.example.helper.User;
import org.example.service.ArticleService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    @Autowired
    private ArticleService service;

    @PostMapping()
    public ResponseEntity<Article> create(@RequestBody final Article article) {
        final var savedArticle = service.create(article);
        return ResponseEntity.ok(savedArticle);
    }

    @GetMapping
    public List<Article> getAllArticles() {
        return service.getAllArticles();
    }


    @GetMapping("/{id}")
    public List<Article> getArticle(@PathVariable UUID uuid) {
        return service.getAllArticles();
    }
}
