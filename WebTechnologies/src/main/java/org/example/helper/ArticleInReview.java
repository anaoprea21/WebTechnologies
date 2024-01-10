package org.example.helper;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.UserEntity;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor

@Data
public class ArticleInReview {
    private UUID uuid;
    private String title;
    private String text;
    private String author;
    private ReviewResponses response;
    private boolean isSentToReview;
    private String reviewer;//pseudonym

}
