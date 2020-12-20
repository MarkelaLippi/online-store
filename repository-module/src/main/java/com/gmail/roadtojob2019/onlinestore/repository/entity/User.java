package com.gmail.roadtojob2019.onlinestore.repository.entity;

import com.gmail.roadtojob2019.onlinestore.repository.converter.RoleConverter;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value=AccessLevel.NONE)
    private Long id;
    private LastMiddleFirstName lastMiddleFirstName;
    private String email;
    @Column(name = "USER_ROLE")
    @Convert(converter = RoleConverter.class)
    private Role role;
    private String password;
}
