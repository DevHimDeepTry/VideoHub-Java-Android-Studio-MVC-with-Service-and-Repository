package com.example.videohub.providers;

import android.content.Context;
import com.example.videohub.repositories.implement.UserRepository;
import com.example.videohub.repositories.interfaces.IUserRepository;

public class RepositoryProvider extends BaseProvider {

    public static IUserRepository provideUserRepository(Context context) {
        return provideSingleton(IUserRepository.class, UserRepository.class, context);
    }
}
