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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    private UserEntity author;

    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity reviewer;

    private ReviewResponses response;

    private boolean reviewing;

    private Integer views;
}
