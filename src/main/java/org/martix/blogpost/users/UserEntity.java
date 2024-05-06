package org.martix.blogpost.users;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.martix.blogpost.comments.CommentEntity;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String roles;

    @OneToMany(mappedBy = "user")
    private List<CommentEntity> comments;
}
