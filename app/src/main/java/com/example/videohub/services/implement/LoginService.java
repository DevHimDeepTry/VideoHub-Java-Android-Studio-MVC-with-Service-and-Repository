package com.example.videohub.services.implement;

import com.example.videohub.models.User;
import com.example.videohub.repositories.interfaces.IUserRepository;
import com.example.videohub.services.interfaces.ILoginService;
import com.example.videohub.utils.Callback;

public class LoginService implements ILoginService {

    private final IUserRepository userRepository;

    public LoginService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void systemAccount(String u, String p, Callback<User> callback) {
        userRepository.findByAccountName(u, new Callback<User>() {
            @Override
            public void onSuccess(User user) {
                // Check if the password matches
                if (user.getPassword().equals(p)) {
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
    }
}
