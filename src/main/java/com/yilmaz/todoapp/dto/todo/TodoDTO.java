package com.yilmaz.todoapp.dto.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {
    private Integer id;
    private String title;
    private String description;
    private Date due_date;
    private Integer user_id;
}
