package org.example.controller;

import org.example.helper.Article;
import org.example.helper.ArticleInReview;
import org.example.helper.User;
import org.example.service.ArticleService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{title}")
    public Article getArticle(@PathVariable String title) {
        return service.getArticle(title);
    }

    @GetMapping("/author/{pseudo}")
    public List<Article> getArticlesByAuthor(@PathVariable String pseudo) {
        return service.getReporterArticles(pseudo);
    }

    @GetMapping("/checkInReview/{title}")
    public ArticleInReview getArticleInReview(@PathVariable String title) {
        return service.checkArticleReviewStatus(title);
    }

    @GetMapping("/checkInReview")
    public List<ArticleInReview> getArticleInReview() {
        return service.getArticlesThatNeedReview();
    }

    @GetMapping("/getArticlesByPopularity")
    public List<Article> getAllArticlesByPopularity() {
        return service.getAllArticlesByPopularity();
    }
}
