package org.example.mapper;

import org.example.entity.ArticleEntity;
import org.example.entity.UserEntity;
import org.example.helper.Article;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ArticleMapper {

    @Autowired
    UserRepository repository;

    public Article toResponse(ArticleEntity article) {
        Article response = new Article();

        response.setText(article.getText());
        response.setTitle(article.getTitle());
        response.setAuthor(article.getAuthor().getPseudonym());
        return response;
    }

    public ArticleEntity toEntity(Article article) {
        ArticleEntity articleEntity = new ArticleEntity();

        articleEntity.setText(article.getText());
        articleEntity.setTitle(article.getTitle());

        final Optional<UserEntity> optionalUser = repository.findUserEntityByPseudonym(article.getAuthor());

        if (optionalUser.isPresent()) {
            final UserEntity user = optionalUser.get();
            articleEntity.setAuthor(user);
            final var articles = user.getArticles();
            articles.add(articleEntity);
            user.setArticles(articles);
            repository.save(user);
        }

        return articleEntity;
    }
}
