package org.jshaw.manner.repository;

import org.jshaw.manner.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsernameOrEmail(String username, String email);

}
