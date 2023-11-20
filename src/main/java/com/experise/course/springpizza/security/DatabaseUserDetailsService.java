package com.experise.course.springpizza.security;

import com.experise.course.springpizza.model.User;
import com.experise.course.springpizza.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> loggedUser = userRepository.findByEmail(username);
        if (loggedUser.isPresent()) {
            return new DatabaseUserDetails(loggedUser.get());

        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
