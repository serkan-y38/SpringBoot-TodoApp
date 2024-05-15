package com.yilmaz.todoapp.service.todo;

import com.yilmaz.todoapp.dto.todo.TodoDTO;
import com.yilmaz.todoapp.entity.Todo;
import com.yilmaz.todoapp.entity.User;
import com.yilmaz.todoapp.repository.TodoRepository;
import com.yilmaz.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

}