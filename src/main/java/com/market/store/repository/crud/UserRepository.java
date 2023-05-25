package com.market.store.repository.crud;

import com.market.store.model.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findOneByUsername(String username);
    Optional<User> findOneByPhone(String username);
    Optional<User> findOneByEmail(String email);
    Optional<User> findOneById(String id);
}
