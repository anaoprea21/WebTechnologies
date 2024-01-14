package org.example.controller;

import org.example.helper.Article;
import org.example.helper.ArticleInReview;
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
    public ResponseEntity<ArticleInReview> create(@RequestBody final CreateArticleDTO createArticleDTO) {
        final boolean isUserReporter = service.checkUserReporter(createArticleDTO.getAuthorUsername());
        if (isUserReporter) {
            final var savedArticle = service.create(createArticleDTO.getArticle(),createArticleDTO.getAuthorUsername());
            return ResponseEntity.ok(savedArticle);
        }
        return (ResponseEntity<ArticleInReview>) ResponseEntity.status(401);
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
    public ArticleInReview getArticleInReview(@PathVariable String title, @RequestBody String username) {
        final boolean isUserReporter = service.checkUserReporter(username);
        if (isUserReporter) {
            return service.checkArticleReviewStatus(title);
        }
        return (ArticleInReview) ResponseEntity.status(401);
    }

    @GetMapping("/checkInReview")
    public List<ArticleInReview> getArticleInReview(@RequestBody String username) {//NO REQUEST BODY
        final boolean isUserReporter = service.checkUserReporter(username);
        if (isUserReporter) {
        return service.getArticlesThatNeedReview();}
        return (List<ArticleInReview>) ResponseEntity.status(401);
    }

    @GetMapping("/getArticlesByPopularity")
    public List<Article> getAllArticlesByPopularity() {
        return service.getAllArticlesByPopularity();
    }
}
