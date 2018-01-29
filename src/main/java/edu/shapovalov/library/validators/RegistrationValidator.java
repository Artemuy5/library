package edu.shapovalov.library.validators;

import edu.shapovalov.library.dto.RegistrationDto;
import edu.shapovalov.library.repositiry.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 05.01.2018.
 */

@Component
public class RegistrationValidator implements Validator {
    private UserRepository userRepository;

    public RegistrationValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // поддерживает для валидации только класс RegistrationDto
    @Override
    public boolean supports(Class<?> aClass) {
        return RegistrationDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegistrationDto registrationDto = RegistrationDto.class.cast(o);

        // если пользователь с таким именем есть, то добавляем ошибку в BindingResult
        if (userRepository.findByUsername(registrationDto.getLogin()) != null) {
            errors.rejectValue("login", "user.exists");
        }

        // если пользователь с таким email есть, то добавляем ошибку в BindingResult
        if (userRepository.findByEmail(registrationDto.getEmail()) != null) {
            errors.rejectValue("email", "email.exists");
        }

        // если confirm password не совпадает с password, то добавляем ошибку в BindingResult
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "different.userForm.password");
        }
    }
}
