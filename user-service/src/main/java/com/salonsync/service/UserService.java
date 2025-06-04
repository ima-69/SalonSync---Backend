package com.salonsync.service;

import com.salonsync.exception.UserException;
import com.salonsync.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);
    User getUserById(long id) throws UserException;
    List<User> getAllUsers();
    User updateUser(long id, User user) throws UserException;
    void deleteUser(long id) throws UserException;
}
