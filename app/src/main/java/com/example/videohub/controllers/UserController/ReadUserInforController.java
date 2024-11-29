package com.example.videohub.controllers.UserController;

import android.content.Context;

import com.example.videohub.models.User;
import com.example.videohub.providers.BusinessServiceProvider;
import com.example.videohub.services.interfaces.IUserServices;
import com.example.videohub.utils.Callback;

import java.util.List;

public class ReadUserInforController {

    private final IUserServices userServices;

    public ReadUserInforController(Context context) {
        this.userServices = BusinessServiceProvider.provideUserServices(context);
    }

    public void getAllUserInfo(Callback<List<User>> callback) {
        userServices.ReadAllUserInfo(callback);
    }
}
