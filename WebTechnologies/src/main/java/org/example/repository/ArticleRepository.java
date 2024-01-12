package org.example.repository;

import org.example.entity.ArticleEntity;
import org.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {

    @Query(value = "SELECT * from ARTICLE a where a.isSentToReview = :status"
            , nativeQuery = true)
    List<ArticleEntity> findArticleWithIsSentToReviewTrue(boolean status);
    @Query(value = "SELECT * from ARTICLE a where a.author = :pseudo"
            , nativeQuery = true)
    Optional<ArticleEntity> findByAuthor(UserEntity pseudo);

}
