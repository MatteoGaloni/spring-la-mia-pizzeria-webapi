package com.experise.course.springpizza.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests()
                .requestMatchers("/ingredients").hasAnyAuthority("ADMIN")
                .requestMatchers("/users").hasAnyAuthority("ADMIN")
                .requestMatchers("/promotions/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/pizzas/create").hasAnyAuthority("ADMIN")
                .requestMatchers("/pizzas/delete/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/pizzas/edit/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/pizzas/**").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/**").permitAll()
                .anyRequest()
                .authenticated()
                .and().formLogin().and().logout();
        http.csrf().disable();
        return http.build();
    }

}
