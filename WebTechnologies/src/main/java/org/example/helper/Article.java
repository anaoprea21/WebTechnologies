package org.example.helper;

import jakarta.persistence.*;
import lombok.Data;
import org.example.entity.StoryEntity;
import org.example.entity.UserEntity;

import java.util.UUID;

@Data
public class Article {
    private UUID uuid;
    private String title;
    private String text;
    private String author;
    private String story;
    private String reviewer;
    private ReviewResponses response;
    private boolean isSentToReview;
    private Integer views;
}
