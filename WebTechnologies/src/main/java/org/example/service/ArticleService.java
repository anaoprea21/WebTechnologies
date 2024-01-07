package org.example.service;

import org.example.entity.ArticleEntity;
import org.example.helper.Article;
import org.example.mapper.ArticleMapper;
import org.example.repository.ArticleRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
