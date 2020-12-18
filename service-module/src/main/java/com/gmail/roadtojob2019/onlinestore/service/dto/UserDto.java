package com.gmail.roadtojob2019.onlinestore.service.dto;

import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDto {
    private Long id;

    @NotBlank(message = "Last name must not be null and must contain at least one non-whitespace character")
    @Size(max = 40, message = "Size of last name must be equal or lower than 40 symbols")
    @Pattern(regexp = "[A-Za-z]+", message = "The last name must consist only of Latin characters")
    private String lastName;

    @Size(max = 40, message = "Size of last name must be equal or lower than 40 symbols")
    @Pattern(regexp = "[A-Za-z]+", message = "The middle name must consist only of Latin characters")
    private String middleName;

    @NotBlank(message = "First name must not be null and must contain at least one non-whitespace character")
    @Size(max = 20, message = "Size of first name must be equal or lower than 20 symbols")
    @Pattern(regexp = "[A-Za-z]+", message = "The first name must consist only of Latin characters")
    private String firstName;

    @NotNull(message = "Email must not be null")
    @Size(max = 50, message = "Size of email must be equal or lower than 50 symbols")
    @Email(message = "The email has to be a well-formed email address")
    private String email;

    @NotNull(message = "Role must not be null")
    private Role role;

    @NotNull(message = "Password must not be null")
    @Size(min = 8, max = 8, message = "Size of password has to be equal 8 symbols")
    private String password;
}
