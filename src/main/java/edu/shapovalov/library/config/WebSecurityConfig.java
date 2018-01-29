package edu.shapovalov.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 05.01.2018.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // конфигурируем защиту запросов
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //разрешаем всем регистрироваться и получать статические ресурсы
                .antMatchers("/login/**", "/registration", "/css/*", "/js/*", "/img/*", "/font-awesome/**")
                    .permitAll()
                //все остальные запросы должны быть аторизированы
                    .anyRequest()
                    .authenticated()
                    .and()
                //регистрируем страницу входа и даем всем доступ
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                //регистрируем путь для выхода и даем всем доступ
                .logout()
                    .permitAll()
                //отключаем CSRF фильтр
                    .and()
                    .csrf()
                    .disable();
    }

    // указываем, как получать пользователя
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
