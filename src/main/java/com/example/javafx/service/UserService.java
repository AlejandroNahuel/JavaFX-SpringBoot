package com.example.javafx.service;

import com.example.javafx.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public List<User> getAllUsers();
    public User getUserById(int id);
    public void addUser(User user);
    public void updateUser(int id, User user);
    public void deleteUser(int id);
    public User getUserByEmail(String email);
}
