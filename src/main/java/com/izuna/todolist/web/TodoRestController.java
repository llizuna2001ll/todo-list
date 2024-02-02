package com.izuna.todolist.web;

import com.izuna.todolist.entities.Todo;
import com.izuna.todolist.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "http://localhost:3000")
public class TodoRestController {

    private final TodoService todoService;

    @Autowired
    public TodoRestController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/user/")
    public ResponseEntity<List<Todo>> getAllTodos(@RequestParam Long userId) {
        List<Todo> todos = todoService.getAllTodosByUserId(userId);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long todoId) {
        Optional<Todo> todo = todoService.getTodoById(todoId);
        return todo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("addTodo/{userId}")
    public ResponseEntity<Todo> createTodoForUser(@PathVariable Long userId, @RequestBody Todo todo) {
        Todo createdTodo = todoService.createTodoForUser(userId, todo);
        if (createdTodo != null) {
            return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{userId}/{todoId}")
    public ResponseEntity<Todo> updateTodoForUser(
            @PathVariable Long userId, @PathVariable Long todoId, @RequestBody Todo todo) {
        Todo updatedTodo = todoService.updateTodoForUser(userId, todoId, todo);
        if (updatedTodo != null) {
            return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long todoId) {
        boolean deleted = todoService.deleteTodoById(todoId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<Todo> updateTodoProgress(
            @PathVariable Long todoId, @RequestParam String progress) {
        Todo updatedTodo = todoService.updateTodoProgress(todoId, progress);
        if (updatedTodo != null) {
            return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
