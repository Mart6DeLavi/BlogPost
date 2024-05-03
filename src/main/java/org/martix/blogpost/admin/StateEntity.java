package org.martix.blogpost.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.martix.blogpost.comments.CommentEntity;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "states")
public class StateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private LocalDate createdAt;
    @Column(nullable = false)
    private LocalDate updatedAt;
    private String text;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CommentEntity> comments;
}
