package org.perez.maintenance.users.repository;

import org.perez.maintenance.users.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
}
