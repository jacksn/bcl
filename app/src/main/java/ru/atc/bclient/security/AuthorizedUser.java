package ru.atc.bclient.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.atc.bclient.model.User;

import java.util.Collections;

import static java.util.Objects.requireNonNull;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private User user;

    public AuthorizedUser(User user) {
        super(user.getLogin(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("USER")));
        this.user = user;
    }

    public static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        AuthorizedUser user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }

    public Integer getId() {
        return user.getId();
    }
}
