package com.example.simplebankapp.user.controller;

import com.example.simplebankapp.user.dto.UserDto;
import com.example.simplebankapp.user.model.User;
import com.example.simplebankapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody UserDto dto) {
        Long id = userService.createUser(dto);
        return ResponseEntity.ok(id);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
