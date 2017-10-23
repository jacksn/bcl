package ru.atc.bclient.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.atc.bclient.model.entity.Account;
import ru.atc.bclient.model.entity.LegalEntity;
import ru.atc.bclient.model.entity.User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private User user;
    private Set<Account> accounts;

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

    public Set<LegalEntity> getLegalEntities() {
        return user.getLegalEntities();
    }

    public Set<Account> getAccounts() {
        if (accounts == null) {
            accounts = user.getLegalEntities().stream()
                    .map(LegalEntity::getAccounts)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toCollection(HashSet::new));
        }
        return accounts;
    }
}
