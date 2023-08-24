package org.perez.maintenance.users.service;

import org.perez.maintenance.users.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);
}
