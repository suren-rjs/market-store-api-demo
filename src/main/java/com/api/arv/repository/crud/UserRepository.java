package com.api.arv.repository.crud;

import com.api.arv.model.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findOneByUsername(String username);
    Optional<User> findByUsernameContainingIgnoreCaseOrProfileNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String username);

    Optional<User> findOneByPhone(String username);

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneById(String id);
}
