package com.izuna.todolist.repositories;

import com.izuna.todolist.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByUserId(Long userId);
}