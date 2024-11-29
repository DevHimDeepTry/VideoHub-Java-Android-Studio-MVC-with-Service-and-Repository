package com.example.videohub.controllers.AuthController;

import android.content.Context;

import com.example.videohub.models.User;
import com.example.videohub.providers.BusinessServiceProvider;
import com.example.videohub.services.interfaces.ILoginService;
import com.example.videohub.utils.Callback;

public class LoginController {

    private final ILoginService loginService;

    public LoginController(Context context) {
        this.loginService = BusinessServiceProvider.provideLoginService(context);
    }

    public void handleLoginWithSystemAccount(String u, String p, Callback<User> callback) {
        loginService.systemAccount(u, p, new Callback<User>() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    callback.onSuccess(user);
                } else {
                    callback.onSuccess(null);
                }
            }

            @Override
            public void onError(Exception e) {
                callback.onSuccess(null);
            }
        });
    };

    public void handleLoginWithGoogleAccount(String googleToken) {
        // Logic for handling Google login
    }

    public void handleLoginWithFacebookAccount(String facebookToken) {
        // Logic for handling Facebook login
    }
}
