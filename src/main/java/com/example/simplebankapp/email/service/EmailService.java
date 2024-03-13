package com.example.simplebankapp.email.service;

import com.example.simplebankapp.email.dto.EmailDetails;

public interface EmailService {

    void sendEmail(EmailDetails emailDetails);
}
