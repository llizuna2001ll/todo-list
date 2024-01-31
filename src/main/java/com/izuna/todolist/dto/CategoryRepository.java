package com.izuna.todolist.dto;

import com.izuna.todolist.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}