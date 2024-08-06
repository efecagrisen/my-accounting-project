package com.ecs.config;

import com.ecs.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**").hasAnyAuthority("Root User","Admin")
                        .requestMatchers("/companies/**").hasAnyAuthority("Root User")
                        .requestMatchers(
                                "/",
                                "/login",
                                "/fragments/**",
                                "/assets/**",
                                "/img/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .successHandler(authSuccessHandler)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)  // Invalidates the session
                        .deleteCookies("JSESSIONID")  // Deletes cookies
                )
                .rememberMe(rememberMe -> rememberMe
                        .tokenValiditySeconds(864000)
                        .key("ecs")
                        .userDetailsService(securityService)
                )
                .build();

    }
}
