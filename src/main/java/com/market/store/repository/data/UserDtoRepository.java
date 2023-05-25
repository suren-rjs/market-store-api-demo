package com.market.store.repository.data;

import com.market.store.model.document.User;
import com.market.store.model.dto.request.auth.SignUp;

import java.util.List;

@SuppressWarnings("unused")
public interface UserDtoRepository {
    void save(SignUp user);

    List<User> findAll();

    User findOneByUsername(String username);
}
