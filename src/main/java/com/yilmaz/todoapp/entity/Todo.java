package com.yilmaz.todoapp.entity;

import com.yilmaz.todoapp.dto.todo.TodoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuppressWarnings("JpaDataSourceORMInspection")
@Table(name = "todo_table")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private Date dueDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public TodoDTO getTodoDTO() {
        return TodoDTO.builder()
                .id(id)
                .title(title)
                .description(description)
                .due_date(dueDate)
                .user_id(user.getId())
                .build();
    }

}
