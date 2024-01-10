package org.example.repository;

import org.example.entity.ArticleEntity;
import org.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {


}
