package com.example.simplebankapp.user.repository;

import com.example.simplebankapp.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
