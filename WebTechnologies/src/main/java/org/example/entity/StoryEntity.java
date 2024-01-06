package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "STORY")
public class StoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


}
