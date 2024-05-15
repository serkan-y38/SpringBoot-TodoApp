package com.yilmaz.todoapp.entity;

import com.yilmaz.todoapp.entity.enums.Role;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuppressWarnings("JpaDataSourceORMInspection")
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;

    @Enumerated(EnumType.STRING)
    private Role role;

}