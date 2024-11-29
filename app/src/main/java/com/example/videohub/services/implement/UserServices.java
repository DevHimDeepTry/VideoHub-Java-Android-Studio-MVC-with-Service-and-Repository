package com.example.videohub.services.implement;

import com.example.videohub.models.User;
import com.example.videohub.repositories.interfaces.IUserRepository;
import com.example.videohub.services.interfaces.IUserServices;
import com.example.videohub.utils.Callback;

import java.util.List;

public class UserServices implements IUserServices {

    private final IUserRepository userRepository;

    public UserServices(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void ReadAllUserInfo(Callback<List<User>> callback) {
        userRepository.getAll(callback);
    }
}
