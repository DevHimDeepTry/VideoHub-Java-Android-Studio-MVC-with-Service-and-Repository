package com.example.videohub.providers;

import android.content.Context;

import com.example.videohub.repositories.implement.CloudinaryRepository;
import com.example.videohub.repositories.implement.UserRepository;
import com.example.videohub.repositories.implement.VideoRepository;
import com.example.videohub.repositories.interfaces.ICloudinaryRepository;
import com.example.videohub.repositories.interfaces.IUserRepository;
import com.example.videohub.repositories.interfaces.IVideoRepository;
import com.example.videohub.services.implement.CloudinaryService;
import com.example.videohub.services.implement.LoginService;
import com.example.videohub.services.implement.RegisterService;
import com.example.videohub.services.implement.UserServices;
import com.example.videohub.services.implement.VideoService;
import com.example.videohub.services.interfaces.ICloudinaryService;
import com.example.videohub.services.interfaces.ILoginService;
import com.example.videohub.services.interfaces.IRegisterService;
import com.example.videohub.services.interfaces.IUserServices;
import com.example.videohub.services.interfaces.IVideoService;

public class BusinessServiceProvider {

    private static IUserRepository userRepositoryInstance;
    private static IUserServices userServicesInstance;
    private static ILoginService loginServiceInstance;

    private static IVideoService videoServiceInstance;
    private static IVideoRepository videoRepositoryInstance;
    private static ICloudinaryService cloudinaryServicesInstance;
    private static ICloudinaryRepository cloudinaryRepositoryInstance;
    private static IRegisterService registerServiceInstance;
    public static ILoginService provideLoginService(Context context) {
        if (loginServiceInstance == null) {
            loginServiceInstance = new LoginService(provideUserRepository(context));
        }
        return loginServiceInstance;
    }
    public static IVideoService provideVideoService(Context context) {
        if (videoServiceInstance == null) {
            videoServiceInstance = new VideoService(provideVideoRepository(context));
        }
        return videoServiceInstance;
    }
    public static IVideoRepository provideVideoRepository(Context context) {
        if (videoRepositoryInstance == null) {
            videoRepositoryInstance = new VideoRepository(context);
        }
        return videoRepositoryInstance;
    }
    public static IUserServices provideUserServices(Context context) {
        if (userServicesInstance == null) {
            userServicesInstance = new UserServices(provideUserRepository(context));
        }
        return userServicesInstance;
    }

    public static IUserRepository provideUserRepository(Context context) {
        if (userRepositoryInstance == null) {
            userRepositoryInstance = new UserRepository(context);
        }
        return userRepositoryInstance;
    }

    public static ICloudinaryService provideCloudinaryServices(Context context) {
        if (cloudinaryServicesInstance == null) {
            cloudinaryServicesInstance = new CloudinaryService(provideCloudinaryRepository(context));
        }
        return cloudinaryServicesInstance;
    }

    public static ICloudinaryRepository provideCloudinaryRepository(Context context) {
        if (cloudinaryRepositoryInstance == null) {
            cloudinaryRepositoryInstance = new CloudinaryRepository(context);
        }
        return cloudinaryRepositoryInstance;
    }

    public static IRegisterService provideRegisterService(Context context) {
        if (registerServiceInstance == null) {
            registerServiceInstance = new RegisterService(provideUserRepository(context));
        }
        return registerServiceInstance;
    }

}
