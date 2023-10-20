package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void createUser(User user);

    Optional<User> findUserByUsername(String username);


    List<User> getAllUser();

    Optional<User> findById(Integer id);


    User updateUser(Integer id);

    User addNewUser(Integer id, User updatedUser);


    void deleteUser(Integer id);
}
