package org.example.helper;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.ArticleEntity;

import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor

@Data
public class Story {

    private UUID id;
    private String title;
    private String description;

}
