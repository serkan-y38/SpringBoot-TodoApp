package com.yilmaz.todoapp.controller;

import com.yilmaz.todoapp.dto.todo.TodoDTO;
import com.yilmaz.todoapp.service.todo.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/create-todo")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO) {
        return todoService.createTodo(todoDTO) ? new ResponseEntity<>("Todo created successfully.", HttpStatus.OK) :
                new ResponseEntity<>("Todo not created. User does not exist.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update-todo/{userId}/{todoId}")
    public ResponseEntity<?> updateTodo(@PathVariable Integer userId, @PathVariable Integer todoId, @RequestBody TodoDTO todoDTO) {
        return todoService.updateTodo(userId, todoId, todoDTO) ? new ResponseEntity<>("Todo updated successfully.", HttpStatus.OK) :
                new ResponseEntity<>("Todo not updated. Todo does not exist.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete-todo/{userId}/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable Integer userId, @PathVariable Integer todoId) {
        return todoService.deleteTodo(userId, todoId) ? new ResponseEntity<>("Todo deleted successfully", HttpStatus.OK) :
                new ResponseEntity<>("Todo not deleted. Todo does not exist.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get-todo/{userId}/{todoId}")
    public ResponseEntity<?> getTodo(@PathVariable Integer userId, @PathVariable Integer todoId) {
        TodoDTO todoDTO = todoService.getTodoById(userId, todoId);
        return (todoDTO != null) ? ResponseEntity.ok(todoDTO) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/get-all-todos/{userId}")
    public ResponseEntity<?> getAllTodos(@PathVariable Integer userId) {
        List<TodoDTO> todoDTO = todoService.getAllTodos(userId);
        return (!todoDTO.isEmpty()) ? ResponseEntity.ok(todoDTO) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /* Paginate todos */
    @GetMapping("/paginate-todos/{userId}")
    ResponseEntity<?> getAllTodos(
            @PathVariable Integer userId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction
    ) {
        List<TodoDTO> paginatedTodos = todoService.getAllTodos(userId, page, size, orderBy, direction);
        return (!paginatedTodos.isEmpty()) ? ResponseEntity.ok(paginatedTodos) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
