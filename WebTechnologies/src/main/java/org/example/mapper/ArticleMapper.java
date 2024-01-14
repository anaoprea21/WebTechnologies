package org.example.mapper;

import org.example.entity.ArticleEntity;
import org.example.entity.UserEntity;
import org.example.exception.UserNotFound;
import org.example.helper.Article;
import org.example.helper.ArticleInReview;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleMapper {

    @Autowired
    UserRepository repository;

    public Article toResponse(ArticleEntity article) {
        Article response = new Article();

        response.setUuid(article.getId());
        response.setText(article.getText());
        response.setTitle(article.getTitle());
        response.setViews(article.getViews());
        response.setAuthor(article.getAuthor().getPseudonym());
        return response;
    }

    public List<Article> moreToResponse(List<ArticleEntity> articleList) {
        List<Article> articles = new ArrayList<>();
        for (ArticleEntity art : articleList) {
            Article response = new Article();

            response.setUuid(art.getId());
            response.setText(art.getText());
            response.setTitle(art.getTitle());
            response.setViews(art.getViews());
            response.setAuthor(art.getAuthor().getPseudonym());
            articles.add(response);
        }
        return articles;
    }

    public ArticleEntity toEntity(Article article) {
        ArticleEntity articleEntity = new ArticleEntity();

        articleEntity.setId(article.getUuid());
        articleEntity.setText(article.getText());
        articleEntity.setTitle(article.getTitle());
        articleEntity.setViews(article.getViews());

        final UserEntity optionalUser = repository.findCustomerByPseudonym(article.getAuthor()).orElseThrow(() -> new UserNotFound("Author not found for this article"));
        articleEntity.setAuthor(optionalUser);

        final var articles = optionalUser.getArticles();
        articles.add(articleEntity);
        optionalUser.setArticles(articles);
        repository.save(optionalUser);

        return articleEntity;
    }

    public ArticleInReview toArticleInReview(ArticleEntity articleEntity) {
        ArticleInReview article = new ArticleInReview();
        article.setText(articleEntity.getText());
        article.setTitle(articleEntity.getTitle());
        article.setAuthor(articleEntity.getAuthor().getPseudonym());
        if (articleEntity.isReviewing()) {
            article.setReviewing(articleEntity.isReviewing());
            article.setResponse(articleEntity.getResponse());
            article.setResponseDescription(articleEntity.getResponseDescription());
        }
        article.setReviewing(articleEntity.isReviewing());
        return article;
    }

    public ArticleEntity articleInReviewToEntity(ArticleInReview article) {
        ArticleEntity articleEntity = new ArticleEntity();

        articleEntity.setId(article.getUuid());
        articleEntity.setText(article.getText());
        articleEntity.setTitle(article.getTitle());

        final UserEntity optionalUser = repository.findCustomerByPseudonym(article.getAuthor()).orElseThrow(() -> new UserNotFound("Author not found for this article"));
        articleEntity.setAuthor(optionalUser);

        if (article.isReviewing()) {
            articleEntity.setReviewing(article.isReviewing());
            articleEntity.setResponse(article.getResponse());
            articleEntity.setResponseDescription(article.getResponseDescription());
        }

        final var articles = optionalUser.getArticles();
        articles.add(articleEntity);
        optionalUser.setArticles(articles);
        repository.save(optionalUser);

        return articleEntity;
    }
}
