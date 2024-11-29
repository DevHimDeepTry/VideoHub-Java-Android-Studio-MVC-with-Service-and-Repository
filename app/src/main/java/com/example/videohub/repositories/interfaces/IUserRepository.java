package com.example.videohub.repositories.interfaces;

import com.example.videohub.models.User;
import com.example.videohub.utils.Callback;


public interface IUserRepository extends IBaseRepository<User> {
    // Additional methods specific to user repository can go here
    void findByAccountName(String accountName, Callback<User> callback);
}
