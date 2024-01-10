package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.helper.ReviewResponses;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "ARTICLE")
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "story_id", nullable = true)
    private StoryEntity story;

    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity reviewer;

    private ReviewResponses response;
    private boolean isSentToReview;

    private Integer views;
}
