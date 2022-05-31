package com.example.demo.controller;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.menu.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {
    @Resource
    UserMapper userMapper;

    @GetMapping
    List<User> getAllUsers() {
        return userMapper.findAll();
    }

    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") long userId) {
        return userMapper.findUserById(userId);
    }

    @GetMapping("/test")
    String getUser() {
        return "user";
    }

    @PostMapping
    void addUser(@RequestBody User user) {
        userMapper.addUser(user);
    }

    @PutMapping
    User updateUserById(@RequestBody User user){
        userMapper.updateUser(user);
        return user;
    }

    @DeleteMapping("/{userId}")
    String deleteUserById(@PathVariable("userId") long userId){
        userMapper.deleteUserById(userId);
        return "sucess";
    }
}
