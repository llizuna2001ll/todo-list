package com.izuna.todolist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.izuna.todolist.enums.Progress;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String category;
    private Date date = Date.from(Instant.now());
    @Enumerated(EnumType.STRING)
    private Progress progress = Progress.TODO;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
