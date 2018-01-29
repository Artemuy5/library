package edu.shapovalov.library.controller;

import edu.shapovalov.library.dto.RegistrationDto;
import edu.shapovalov.library.service.UserService;
import edu.shapovalov.library.validators.RegistrationValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 05.01.2018.
 */

@Controller
public class UserController {
    private UserService userService;
    private RegistrationValidator registrationValidator;

    public UserController(UserService userService, RegistrationValidator registrationValidator) {
        this.userService = userService;
        this.registrationValidator = registrationValidator;
    }

    //регистрируем валидатор для формы регистрации
    @InitBinder
    public void addRegistrationValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(registrationValidator);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("user", new RegistrationDto());
        return "registration";
    }

    //валидируем форму и в случае ошибки опять показываем страницу с формой
    @PostMapping("/registration")
    public String register(@ModelAttribute("user") @Valid RegistrationDto registrationDto,
                           /* хранит результаты валидации */ BindingResult bindingResult) {
        registrationValidator.validate(registrationDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.register(registrationDto);
        return "redirect:/login";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
