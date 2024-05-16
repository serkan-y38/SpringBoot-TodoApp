package com.yilmaz.todoapp.repository;

import com.yilmaz.todoapp.entity.Todo;
import com.yilmaz.todoapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    Optional<Todo> findByIdAndUser(Integer id, User user);

    List<Todo> findAllByUser(User user);

    Page<Todo> findAllByUser(User user, Pageable pageable);

}
