package com.example.videohub.services.interfaces;

import com.example.videohub.models.User;
import com.example.videohub.utils.Callback;

import java.util.List;

public interface IUserServices {
    void ReadAllUserInfo(Callback<List<User>> callback);
}
