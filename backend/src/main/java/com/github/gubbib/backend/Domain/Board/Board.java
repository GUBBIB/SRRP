package com.github.gubbib.backend.Domain.Board;

import com.github.gubbib.backend.Domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    public static Board create(String name, String description) {
        Board b = new Board();

        b.name = name;
        b.description = description;

        return b;
    }
}
