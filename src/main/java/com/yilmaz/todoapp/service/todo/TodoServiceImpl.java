package com.yilmaz.todoapp.service.todo;

import com.yilmaz.todoapp.dto.todo.TodoDTO;
import com.yilmaz.todoapp.entity.Todo;
import com.yilmaz.todoapp.entity.User;
import com.yilmaz.todoapp.repository.TodoRepository;
import com.yilmaz.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Override
    public boolean createTodo(TodoDTO todoDTO) {
        boolean success = false;
        Optional<User> optionalUser = userRepository.findById(todoDTO.getUser_id());
        if (optionalUser.isPresent()) {
            todoRepository.save(
                    Todo.builder()
                            .title(todoDTO.getTitle())
                            .description(todoDTO.getDescription())
                            .dueDate(todoDTO.getDue_date())
                            .user(optionalUser.get())
                            .build()
            );
            success = true;
        }
        return success;
    }

    @Override
    public boolean updateTodo(Integer userId, Integer todoId, TodoDTO todoDTO) {
        boolean success = false;
        Optional<Todo> optionalTodo = todoRepository.findById(todoId);
        if (optionalTodo.isPresent() && optionalTodo.get().getUser().getId().equals(userId)) {
            Todo todo = optionalTodo.get();
            todo.setTitle(todoDTO.getTitle());
            todo.setDescription(todoDTO.getDescription());
            todo.setDueDate(todoDTO.getDue_date());
            todoRepository.save(todo);
            success = true;
        }
        return success;
    }

    @Override
    public boolean deleteTodo(Integer userId, Integer todoId) {
        boolean success = false;
        Optional<Todo> optionalTodo = todoRepository.findById(todoId);
        if (optionalTodo.isPresent() && optionalTodo.get().getUser().getId().equals(userId)) {
            todoRepository.delete(optionalTodo.get());
            success = true;
        }
        return success;
    }

    @Override
    public TodoDTO getTodoById(Integer userId, Integer todoId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Optional<Todo> optionalTodoDTO = todoRepository.findByIdAndUser(todoId, optionalUser.get());
            return optionalTodoDTO.map(Todo::getTodoDTO).orElse(null);
        }
        return null;
    }

    @Override
    public List<TodoDTO> getAllTodos(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(user -> todoRepository.findAllByUser(user).stream().map(Todo::getTodoDTO).collect(Collectors.toList())).orElse(null);
    }

    @Override
    public List<TodoDTO> getAllTodos(Integer userId, Integer page, Integer size, String orderBy, String direction) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
            Page<Todo> todoPage = todoRepository.findAllByUser(optionalUser.get(), pageRequest);
            return todoPage.stream().map(Todo::getTodoDTO).collect(Collectors.toList());
        }
        return null;
    }

}