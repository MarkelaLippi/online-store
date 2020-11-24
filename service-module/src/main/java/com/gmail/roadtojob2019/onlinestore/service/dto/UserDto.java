package com.gmail.roadtojob2019.onlinestore.service.dto;

import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDto {
    private int id;
    @NonNull
    private String lastName;
    private String middleName;
    @NonNull
    private String firstName;
    @NonNull
    private String email;
    @NonNull
    private Role role;
}
