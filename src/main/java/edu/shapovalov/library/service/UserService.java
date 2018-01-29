package edu.shapovalov.library.service;

import edu.shapovalov.library.dto.RegistrationDto;
import edu.shapovalov.library.entity.User;

import java.util.List;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 05.01.2018.
 */

public interface UserService {
    void register(RegistrationDto registrationDto);
}
