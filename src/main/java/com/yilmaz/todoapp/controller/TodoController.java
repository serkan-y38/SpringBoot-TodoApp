package com.yilmaz.todoapp.controller;

import com.yilmaz.todoapp.dto.todo.TodoDTO;
import com.yilmaz.todoapp.service.todo.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/create-todo")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO) {
        return todoService.createTodo(todoDTO) ? ResponseEntity.status(HttpStatus.CREATED).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
