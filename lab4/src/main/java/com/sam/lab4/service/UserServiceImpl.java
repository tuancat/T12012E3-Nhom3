package com.sam.lab4.service;

import com.sam.lab4.model.User;
import com.sam.lab4.repository.UserRepositor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepositor userRepositor;
    @Override
    public List<User> userList() {
        return userRepositor.findAll();
    }

    @Override
    public void addUser(User user) {
        userRepositor.save(user);
    }
}
