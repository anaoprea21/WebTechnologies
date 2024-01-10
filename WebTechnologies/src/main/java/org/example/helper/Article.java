package org.example.helper;

import lombok.Data;

import java.util.UUID;

@Data
public class Article {
    private UUID uuid;
    private String title;
    private String text;
    private String author;
}
