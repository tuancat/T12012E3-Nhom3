package com.sam.lab4.service;

import com.sam.lab4.model.User;
import com.sam.lab4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepositor;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public List<User> userList() {
        return userRepositor.findAll();
    }

    @Override
    public void addUser(User user) {
        String password = user.getPass();
        user.setPass(bCryptPasswordEncoder.encode(password));
        userRepositor.save(user);
    }
}
