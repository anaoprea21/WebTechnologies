package org.example.controller;

import org.example.exception.Unauthorized;
import org.example.helper.Article;
import org.example.helper.CreateArticleDTO;
import org.example.service.ArticleService;
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
    public ResponseEntity<Object> create(@RequestBody final CreateArticleDTO createArticleDTO) {
        final boolean isUserReporter = service.checkUserReporter(createArticleDTO.getAuthorUsername());
        if (isUserReporter) {
            final var savedArticle = service.create(createArticleDTO.getArticle(), createArticleDTO.getAuthorUsername());
            return ResponseEntity.ok(savedArticle);
        } else {
            throw new Unauthorized("Customer not authorized");
        }
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

    @GetMapping("/checkInReview/{title}/{username}")
    public Object getArticleInReview(@PathVariable String title, @PathVariable String username) {

        final boolean isUserReporter = service.checkUserReporter(username);
        if (isUserReporter) {
            return service.checkArticleReviewStatus(title);
        } else {
            throw new Unauthorized("Customer not authorized");
        }
    }

    @GetMapping("/checkInReview/author/{username}")
    public Object getArticleInReviewByAuthor(@PathVariable String username) {

        final boolean isUserReporter = service.checkUserReporter(username);
        if (isUserReporter) {
            return service.getArticlesThatNeedReview();
        } else {
            throw new Unauthorized("Customer not authorized");
        }
    }

    @GetMapping("/getArticlesByPopularity")
    public List<Article> getAllArticlesByPopularity() {
        return service.getAllArticlesByPopularity();
    }

    @PutMapping("/edit")
    public ResponseEntity<Object> edit(@RequestBody final CreateArticleDTO editArticleDTO) {
        final boolean isUserReporter = service.checkUserReporter(editArticleDTO.getAuthorUsername());
        if (isUserReporter) {
            final var savedArticle = service.update(editArticleDTO.getArticle(), editArticleDTO.getAuthorUsername());
            return ResponseEntity.ok(savedArticle);
        } else {
            throw new Unauthorized("Customer not authorized");
        }
    }
}
