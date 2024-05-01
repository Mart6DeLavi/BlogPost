package org.martix.blogpost.comments;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.martix.blogpost.admin.StateEntity;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * TODO: писатель должен подставляться автоматически
     * Функционал: должно браться имя и фамилия с базы данных писателей.
     * То-есть программа должна брать данные с аккаунта, через который
     * пользователь залогинился на сайт
     * */
    @Column(nullable = false)
    private String author;
    private LocalDate dateOfWriting;
    private String text;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private StateEntity state;
}
