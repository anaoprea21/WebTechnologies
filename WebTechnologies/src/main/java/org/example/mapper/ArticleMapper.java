package org.example.mapper;

import org.example.entity.ArticleEntity;
import org.example.entity.UserEntity;
import org.example.exception.ArticleNotFound;
import org.example.exception.UserNotFound;
import org.example.helper.Article;
import org.example.helper.ArticleInReview;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ArticleMapper {

    @Autowired
    UserRepository repository;

    public Article toResponse(ArticleEntity article) {
        Article response = new Article();

        response.setUuid(article.getId());
        response.setText(article.getText());
        response.setTitle(article.getTitle());
        response.setAuthor(article.getAuthor().getPseudonym());
        response.setReviewer(article.getReviewer().getPseudonym());
        response.setStory(article.getStory().getTitle());
        response.setSentToReview(article.isSentToReview());
        return response;
    }

    public List<Article> moreToResponse(List<ArticleEntity> articleList) {
        List<Article> articles = new ArrayList<>();
        for (ArticleEntity art : articleList) {
            Article response = new Article();

            response.setUuid(art.getId());
            response.setText(art.getText());
            response.setTitle(art.getTitle());
            response.setAuthor(art.getAuthor().getPseudonym());
            response.setReviewer(art.getReviewer().getPseudonym());
            response.setStory(art.getStory().getTitle());
            response.setSentToReview(art.isSentToReview());
            articles.add(response);
        }
        return articles;
    }

    public ArticleEntity toEntity(Article article) {
        ArticleEntity articleEntity = new ArticleEntity();

        articleEntity.setText(article.getText());
        articleEntity.setTitle(article.getTitle());

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
        article.setUuid(articleEntity.getId());
        article.setText(articleEntity.getText());
        article.setTitle(articleEntity.getTitle());
        article.setAuthor(articleEntity.getAuthor().getPseudonym());
        article.setReviewer(articleEntity.getReviewer().getPseudonym());
        article.setSentToReview(articleEntity.isSentToReview());
        return article;
    }
}
