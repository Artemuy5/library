package edu.shapovalov.library.service;

import edu.shapovalov.library.entity.User;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 06.01.2018
 */

public interface SecurityService {
    User getCurrentUser();
}
