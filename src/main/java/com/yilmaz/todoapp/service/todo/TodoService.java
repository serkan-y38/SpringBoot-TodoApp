package com.yilmaz.todoapp.service.todo;

import com.yilmaz.todoapp.dto.todo.TodoDTO;

import java.util.List;

public interface TodoService {

    boolean createTodo(TodoDTO todoDTO);

    boolean updateTodo(Integer userId, Integer todoId, TodoDTO todoDTO);

    boolean deleteTodo(Integer userId, Integer todoId);

    TodoDTO getTodoById(Integer userId, Integer todoId);

    List<TodoDTO> getAllTodos(Integer userId);

    List<TodoDTO> getAllTodos(Integer userId, Integer page, Integer size, String orderBy, String direction);

    List<TodoDTO> searchTodos(String query, Integer userId, Integer page, Integer size, String orderBy, String direction);

}
