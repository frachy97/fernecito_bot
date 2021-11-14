package com.ferreyra.service;

import com.ferreyra.exceptions.InvalidRequestException;
import com.ferreyra.exceptions.RecordAlreadyExistsException;
import com.ferreyra.exceptions.RecordNotExistsException;
import com.ferreyra.model.User;
import com.ferreyra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User newUser) throws InvalidRequestException, RecordAlreadyExistsException {
        if (isNull(newUser))
            throw new InvalidRequestException("You need to provide the new user info");
        User saved = userRepository.save(newUser);
        if (isNull(saved))
            throw new RecordAlreadyExistsException("Email is already added");
        return saved;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findById(Integer id) throws RecordNotExistsException {
        return userRepository.findById(id).orElseThrow(() -> new RecordNotExistsException("No user found with provided Id"));
    }

    public User disableUser(Integer userId) throws RecordNotExistsException {
        User user = findById(userId);
        user.setActive(false);
        return userRepository.save(user);
    }

/*    public User updateUser(Integer id, UpdateUserDto newUserData) throws RecordNotExistsException {
        Optional<User> userById = userRepository.findById(id);
        if(!userById.isPresent())
            throw new RecordNotExistsException("Id provided is not exists on users data");
        User updated = userById.get();
        updated.updateUser(newUserData);
        userRepository.updateUser(updated.getName(), updated.getLastname(), updated.getPassword(),
                updated.getCity().getId(), updated.getUserType().toString(), updated.getUserStatus().toString(),
                updated.getEmail());
        return updated;
    }*/

}
