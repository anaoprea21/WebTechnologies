package org.example.helper;

import lombok.Data;

@Data
public class Article {
    private String title;
    private String text;
    private String author;
    //    private String reviewer;
    private ReviewResponses response;
}
