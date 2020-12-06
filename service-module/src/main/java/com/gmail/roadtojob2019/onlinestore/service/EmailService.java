package com.gmail.roadtojob2019.onlinestore.service;

public interface EmailService {

    void sendNewUserPasswordToEmail(String userEmail, String mailSubject, String newPassword);

}
