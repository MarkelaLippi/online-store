package com.gmail.roadtojob2019.onlinestore.service.dto;

import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDto {
    private int id;
    private String lastName;
    private String firstName;
    private String email;
    private Role role;
}
