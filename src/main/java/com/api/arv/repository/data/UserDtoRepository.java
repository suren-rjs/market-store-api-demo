package com.api.arv.repository.data;

import com.api.arv.model.document.User;
import com.api.arv.model.dto.request.auth.SignUp;

import java.util.List;

@SuppressWarnings("unused")
public interface UserDtoRepository {
    void save(SignUp user);

    List<User> findAll();

    User findOneByUsername(String username);

    String changeActiveStatusById(String id);
}
