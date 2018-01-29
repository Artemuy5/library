package edu.shapovalov.library.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 05.01.2018.
 */

@Data
public class RegistrationDto {
    @Size(min = 2, max = 16)
    private String login;

    @Size(min = 8)
    private String password;

    @Size(min = 8, message = "")
    private String confirmPassword;

    @Email
    private String email;
}
