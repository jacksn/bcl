package ru.atc.bclient.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.atc.bclient.model.entity.User;
import ru.atc.bclient.model.repository.UserRepository;
import ru.atc.bclient.web.security.AuthorizedUser;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь с логином " + login + " не найден.");
        }
        return new AuthorizedUser(user);
    }
}
