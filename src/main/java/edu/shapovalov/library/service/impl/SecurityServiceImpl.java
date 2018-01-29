package edu.shapovalov.library.service.impl;

import edu.shapovalov.library.entity.User;
import edu.shapovalov.library.repositiry.UserRepository;
import edu.shapovalov.library.service.SecurityService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 06.01.2018
 */

@Service
public class SecurityServiceImpl implements SecurityService {
    private UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        UserDetails userDetails = UserDetails.class.cast(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        String username = userDetails.getUsername();
        return userRepository.findByUsername(username);
    }
}
