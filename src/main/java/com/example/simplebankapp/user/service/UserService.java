package com.example.simplebankapp.user.service;

import com.example.simplebankapp.user.dto.UserDto;
import com.example.simplebankapp.user.model.User;
import com.example.simplebankapp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public Long createUser(UserDto dto) {
        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();

        User userdb = userRepository.save(user);



        return userdb.getId();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
