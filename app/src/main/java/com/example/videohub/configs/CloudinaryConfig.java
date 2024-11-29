package com.example.videohub.configs;

import android.content.Context;

import com.cloudinary.android.MediaManager;
import com.example.videohub.evironments.env;

import java.util.HashMap;
import java.util.Map;

public class CloudinaryConfig {
    private static boolean isInitialized = false;

    public static void initConnectCloudinary(Context context) {
        if (!isInitialized) {
            env env = new env();
            Map<String, Object> config = new HashMap<>();
            config.put("cloud_name",env.getCLOUDINARY_CLOUD_NAME());
            config.put("api_key", env.getCLOUDINARY_API_KEY());
            config.put("api_secret", env.getCLOUDINARY_API_SECRET());

            MediaManager.init(context, config);
            isInitialized = true;
        }
    }
}
