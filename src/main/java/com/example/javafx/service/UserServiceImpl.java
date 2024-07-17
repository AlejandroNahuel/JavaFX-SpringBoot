package com.example.javafx.service;

import com.example.javafx.dao.UserDAO;
import com.example.javafx.model.Rol;
import com.example.javafx.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDAO userDAO;

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userDAO.findById(id).orElse(null);
    }

    @Override
    public void addUser(User user) {
        userDAO.save(user);
    }

    @Override
    public void updateUser(int id, User user) {
        if (userDAO.findById(id).isPresent()){
            user.setId(id);
            userDAO.save(user);
        }
    }

    @Override
    public void deleteUser(int id) {
        if(userDAO.findById(id).isPresent()){
            userDAO.deleteById(id);
        }
    }
    @Override
    public User getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Usuario o contraseña inválidos");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapAuthorityRoles(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapAuthorityRoles(Collection<Rol> roles){
        return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());
    }
}
