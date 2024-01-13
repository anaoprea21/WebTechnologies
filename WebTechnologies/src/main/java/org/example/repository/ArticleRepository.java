package org.example.repository;

import org.example.entity.ArticleEntity;
import org.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {

    @Query(value = "select * from ARTICLE a where a.reviewing = :reviewing"
            , nativeQuery = true)
    List<ArticleEntity> findArticleWithIsSentToReviewTrue(@Param("reviewing") boolean reviewing);

    List<ArticleEntity> findByAuthor(UserEntity pseudo);
    Optional<ArticleEntity> findByTitle(String title);

    List<ArticleEntity> findByOrderByViewsAsc();


}
