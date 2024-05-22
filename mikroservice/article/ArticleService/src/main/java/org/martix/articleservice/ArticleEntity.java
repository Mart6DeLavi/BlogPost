package org.martix.articleservice;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "articles")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            unique = true,
            name = "title")
    private String title;

    @Column(
            nullable = false,
            name = "author")
    private String author;

    @Column(
            nullable = false,
            name = "createdAt")
    private LocalDate createdAt;

    @Column(
            nullable = false,
            name = "updatedAt")
    private LocalDate updatedAt;

    //TODO:подумать - нужен ли текст статьи в бд
    private String text;

    /*
    * @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CommentEntity> comments;
    * */

}
