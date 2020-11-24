package com.gmail.roadtojob2019.onlinestore.repository.entity;

import com.gmail.roadtojob2019.onlinestore.repository.converter.RoleConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    private LastMiddleFirstName lastMiddleFirstName;
    @Column(name = "email")
    private String email;
    @Column(name = "user_role")
    @Convert(converter = RoleConverter.class)
    private Role role;
}
