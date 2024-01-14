package org.example.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String responseDescription;
    private boolean reviewing;
}
