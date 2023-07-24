package com.example.syrus_media_mobile.Models;

public class User {
    int ID;
    String Username;
    String Password;
    String ConfirmPassword;
    String Email;

    String Error;
    String Success;
    public String getError() {
        return Error;
    }

    public User setError(String error) {
        Error = error;
        return null;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }


    public User(String username, String password, String confirmPassword, String email, String phone) {
        Username = username;
        Password = password;
        ConfirmPassword = confirmPassword;
        Email = email;
        Phone = phone;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    String Phone;
    public User() {
    }
    public User(String username, String password) {
        Username = username;
        Password = password;
    }

    public User(String username, String password, String email, String phone) {
        Username = username;
        Password = password;
        Email = email;
        Phone = phone;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }


}
