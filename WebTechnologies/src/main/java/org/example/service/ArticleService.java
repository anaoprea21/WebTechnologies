package org.example.service;

import org.example.entity.ArticleEntity;
import org.example.exception.ArticleNotFound;
import org.example.exception.UserNotFound;
import org.example.helper.Article;
import org.example.helper.ArticleInReview;
import org.example.mapper.ArticleMapper;
import org.example.repository.ArticleRepository;
import org.example.repository.UserRepository;
import org.mapstruct.ap.shaded.freemarker.core.ReturnInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Article getArticle(String pseudo) {
        ArticleEntity optionalArticle = repository.findByAuthor(pseudo)
                .orElseThrow(() -> new ArticleNotFound("Article not found for author " + pseudo));
        return mapper.toResponse(optionalArticle);
    }

    public ArticleInReview checkArticleReviewStatus(String pseudo) {
        ArticleEntity articleEntity = repository.findByAuthor(pseudo).orElseThrow(() -> new ArticleNotFound("Article not found for author " + pseudo));
        return mapper.toArticleInReview(articleEntity);
    }

    public List<ArticleInReview> getArticlesThatNeedReview() {
        List<ArticleEntity> articleEntities= repository.findArticleWithIsSentToReviewTrue(true);
        List<ArticleInReview> articleInReviews=new ArrayList<>();
        for(ArticleEntity art:articleEntities){
            articleInReviews.add(mapper.toArticleInReview(art));
        }
        return articleInReviews;
    }

    //get reporter articles

    //get most popular articles after nr of views
}
