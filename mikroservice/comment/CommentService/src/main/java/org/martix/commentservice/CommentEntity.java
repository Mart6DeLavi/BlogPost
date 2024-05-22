package org.martix.commentservice;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            name = "author")
    private String author;

    @Column(
            nullable = false,
            name = "dateOfWriting"
    )
    private LocalDate dateOfWriting;

    @Column(
            nullable = false,
            name = "text"
    )
    private String text;

    @Column(
            nullable = false,
            name = "articleId"
    )
    private Long articleId;
}
