package org.example.service;

import org.example.entity.ArticleEntity;
import org.example.helper.Article;
import org.example.helper.ArticleInReview;
import org.example.mapper.ArticleMapper;
import org.example.repository.ArticleRepository;
import org.example.repository.UserRepository;
import org.mapstruct.ap.shaded.freemarker.core.ReturnInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleMapper mapper;

    public Article create(final Article article) {
        final ArticleEntity entity = mapper.toEntity(article);
        repository.save(entity);

        return mapper.toResponse(entity);
    }

    public List<Article> getAllArticles() {
        return mapper.moreToResponse(repository.findAll());
    }

    public Article getArticle(UUID uuid) {
        ArticleEntity optionalArticle = repository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND"));// fa cu exception handler ca in lab10
        return mapper.toResponse(optionalArticle);
    }

    public ArticleInReview checkArticleReviewStatus(UUID uuid) {
        ArticleEntity articleEntity = repository.findById(uuid).orElseThrow(() -> new RuntimeException("NOT_FOUND"));
        return mapper.toArticleInReview(articleEntity);
    }

//    public ArticleInReview getArticlesThatNeedReview() {
//        ArticleEntity articleEntity = repository.findBy(uuid).orElseThrow(() -> new RuntimeException("NOT_FOUND"));
//        return mapper.toArticleInReview(articleEntity);
//    }

    //get reporter articles

    //get most popular articles after nr of views
}
