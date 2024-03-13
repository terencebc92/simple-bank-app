package com.example.simplebankapp.account.model;

import com.example.simplebankapp.user.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String accountNumber;
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_id")// child
    @JsonBackReference
    private User user;
}
