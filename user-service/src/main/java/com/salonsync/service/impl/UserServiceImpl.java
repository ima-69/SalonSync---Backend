package com.salonsync.service.impl;

import com.salonsync.exception.UserException;
import com.salonsync.model.User;
import com.salonsync.repository.UserRepository;
import com.salonsync.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(long id) throws UserException {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        }
        throw new UserException("User not found");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(long id, User userDetails) throws UserException {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserException("User not found");
        }

        User existingUser = user.get();
        existingUser.setFirstName(userDetails.getFirstName());
        existingUser.setLastName(userDetails.getLastName());
        existingUser.setUsername(userDetails.getUsername());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPhone(userDetails.getPhone());
        existingUser.setRole(userDetails.getRole());
        existingUser.setUpdatedAt(java.time.LocalDateTime.now());
        existingUser.setPassword(userDetails.getPassword());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(long id) throws UserException {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new UserException("User not found");
        }
        userRepository.deleteById(user.get().getId());
    }
}
