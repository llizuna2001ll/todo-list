package com.izuna.todolist.services;

import com.izuna.todolist.entities.Todo;
import com.izuna.todolist.entities.User;
import com.izuna.todolist.enums.Progress;
import com.izuna.todolist.repositories.TodoRepository;
import com.izuna.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public List<Todo> getAllTodosByUserId(Long userId) {
        return todoRepository.findAllByUserId(userId);
    }

    public Optional<Todo> getTodoById(Long todoId) {
        return todoRepository.findById(todoId);
    }

    public Todo createTodoForUser(Long userId, Todo todo) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            todo.setUser(user);
            return todoRepository.save(todo);
        }
        return null;
    }

    public Todo updateTodoForUser(Long userId, Long todoId, Todo todo) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Todo> optionalTodo = todoRepository.findById(todoId);
            if (optionalTodo.isPresent()) {
                Todo existingTodo = optionalTodo.get();
                existingTodo.setContent(todo.getContent());
                existingTodo.setCategory(todo.getCategory());
                existingTodo.setDate(todo.getDate());
                existingTodo.setProgress(todo.getProgress());
                existingTodo.setUser(user);
                return todoRepository.save(existingTodo);
            }
        }
        return null;
    }

    public boolean deleteTodoById(Long todoId) {
        if (todoRepository.existsById(todoId)) {
            todoRepository.deleteById(todoId);
            return true;
        }
        return false;
    }

    public Todo updateTodoProgress(Long todoId, String progress) {
        Optional<Todo> optionalTodo = todoRepository.findById(todoId);
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            switch (progress.toUpperCase()) {
                case "TODO" -> todo.setProgress(Progress.TODO);
                case "ONGOING" -> todo.setProgress(Progress.ONGOING);
                case "DONE" -> todo.setProgress(Progress.DONE);
                default -> {
                    return null;
                }
            }
            return todoRepository.save(todo);
        }
        return null;
    }
}
