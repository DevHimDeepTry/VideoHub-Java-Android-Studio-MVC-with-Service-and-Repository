package com.example.videohub.services.interfaces;

import com.example.videohub.models.User;
import com.example.videohub.utils.Callback;

public interface ILoginService {
    void systemAccount(String u, String p, Callback<User> callback);
}
