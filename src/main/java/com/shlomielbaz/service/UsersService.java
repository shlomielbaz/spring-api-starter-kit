package com.shlomielbaz.service;

import com.shlomielbaz.entity.User;
import com.shlomielbaz.interfaces.ICrusService;
import com.shlomielbaz.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersService implements ICrusService<User> {
    private UserRepository userRepository;

    @Override
    public User create(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public User getById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.get();
    }
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(User entity) {
        User existingUser = userRepository.findById(entity.getId()).get();
        existingUser.setFirstName(entity.getFirstName());
        existingUser.setLastName(entity.getLastName());
        existingUser.setEmail(entity.getEmail());
        User updatedUser = userRepository.save(existingUser);
        return updatedUser;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}