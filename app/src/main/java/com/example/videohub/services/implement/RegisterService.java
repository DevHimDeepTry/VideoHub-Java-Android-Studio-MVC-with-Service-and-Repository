package com.example.videohub.services.implement;

import com.example.videohub.models.User;
import com.example.videohub.repositories.interfaces.IUserRepository;
import com.example.videohub.services.interfaces.IRegisterService;
import com.example.videohub.utils.Callback;


public class RegisterService implements IRegisterService {
    private final IUserRepository userRepository;

    public RegisterService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void registerSystemAccount(User user, Callback<Boolean> callback) {
        userRepository.insert(user.getId(), user, new Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                callback.onSuccess(true);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

}
