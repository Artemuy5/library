package edu.shapovalov.library.service.impl;

import edu.shapovalov.library.dto.RegistrationDto;
import edu.shapovalov.library.entity.User;
import edu.shapovalov.library.repositiry.RoleRepository;
import edu.shapovalov.library.repositiry.UserRepository;
import edu.shapovalov.library.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 05.01.2018.
 */

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void register(RegistrationDto registrationDto) {
        User user = new User();
        user.setUsername(registrationDto.getLogin());
        user.setPassword(registrationDto.getPassword());
        user.setEmail(registrationDto.getEmail());
        user.setRole(roleRepository.findByName("user"));
        userRepository.save(user);
    }
}
