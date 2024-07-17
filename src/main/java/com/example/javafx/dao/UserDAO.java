package com.example.javafx.dao;

import com.example.javafx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserDAO extends JpaRepository<User, Integer>{
   User findByEmail(String username);
}
