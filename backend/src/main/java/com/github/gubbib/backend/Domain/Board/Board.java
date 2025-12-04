package com.github.gubbib.backend.Domain.Board;

import com.github.gubbib.backend.Domain.BaseEntity;
import com.github.gubbib.backend.Domain.Post.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "boards")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name",  nullable = false, length = 255)
    private String name;
    @Column(name = "description",  nullable = false, length = 255)
    private String description;

    @OneToMany(mappedBy = "board")
    private List<Post> posts = new ArrayList<>();

}
