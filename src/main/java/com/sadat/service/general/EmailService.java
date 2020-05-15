package com.sadat.service.general;

public interface EmailService {

    void sendPassword(String to, String username, String password);
    void forgetPassword(String to);
}
