package com.example.videohub.services.interfaces;

import com.example.videohub.models.User;
import com.example.videohub.utils.Callback;

public interface IRegisterService {
    void registerSystemAccount(User user, Callback<Boolean> callback);
}
