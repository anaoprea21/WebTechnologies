package org.example.service;

import org.example.entity.ArticleEntity;
import org.example.entity.UserEntity;
import org.example.exception.ArticleNotFound;
import org.example.exception.UserNotFound;
import org.example.helper.Article;
import org.example.helper.ArticleInReview;
import org.example.helper.ReviewResponses;
import org.example.helper.UserRole;
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

    public ArticleInReview create(final Article article, final String username) {
        UserEntity user = userRepository.findCustomerByUsername(username).orElseThrow(() -> new UserNotFound("User not found for username " + username));
        article.setAuthor(user.getPseudonym());

        ArticleInReview articleInReview = new ArticleInReview();

        articleInReview.setText(article.getText());
        articleInReview.setTitle(article.getTitle());
        articleInReview.setAuthor(article.getAuthor());
        articleInReview.setReviewing(article.isReviewing());

        if (article.isReviewing()) {
            articleInReview.setResponse(ReviewResponses.PENDING);
            articleInReview.setResponseDescription("");
        }

        final ArticleEntity entity = mapper.articleInReviewToEntity(articleInReview);
        entity.setViews(0);
        repository.save(entity);

        final var articles = user.getArticles();
        articles.add(entity);
        user.setArticles(articles);
        userRepository.save(user);

        return mapper.toArticleInReview(entity);
    }

    public List<Article> getAllArticles() {
        List<Article> articles = mapper.moreToResponse(repository.findAll());
        for (Article art : articles) {
            art.setViews(art.getViews() + 1);
        }
        return articles;

    }

    public Article getArticle(String title) {
        ArticleEntity optionalArticle = repository.findByTitle(title)
                .orElseThrow(() -> new ArticleNotFound("Article not found for title " + title));

        optionalArticle.setViews(optionalArticle.getViews() + 1);
        return mapper.toResponse(optionalArticle);
    }

    public ArticleInReview checkArticleReviewStatus(String title) {
        ArticleEntity optionalArticle = repository.findByTitle(title)
                .orElseThrow(() -> new ArticleNotFound("Article not found for title " + title));
        return mapper.toArticleInReview(optionalArticle);
    }

    public List<ArticleInReview> getArticlesThatNeedReview() {
        List<ArticleEntity> articleEntities = repository.findArticleWithIsSentToReviewTrue(true);
        List<ArticleInReview> articleInReviews = new ArrayList<>();
        for (ArticleEntity art : articleEntities) {
            articleInReviews.add(mapper.toArticleInReview(art));
        }
        return articleInReviews;
    }

    public List<Article> getReporterArticles(String pseudo) {
        UserEntity user = userRepository.findCustomerByPseudonym(pseudo).orElseThrow(() -> new UserNotFound("User not found for pseudonym " + pseudo));

        List<ArticleEntity> articlesEntities = repository.findByAuthor(user);
        List<Article> articles = new ArrayList<>();
        for (ArticleEntity art : articlesEntities) {
            art.setViews(art.getViews() + 1);
            articles.add(mapper.toResponse(art));
        }

        return articles;
    }

    public List<Article> getAllArticlesByPopularity() {
        return mapper.moreToResponse(repository.findByOrderByViewsAsc());
    }

    public boolean checkUserReporter(String authorUsername) {
        UserEntity user = userRepository.findCustomerByUsername(authorUsername).orElseThrow(() -> new UserNotFound("User not found for username " + authorUsername));
        return user.getRole().equals(UserRole.REPORTER);
    }


    //add roles and validators
    //views
}
