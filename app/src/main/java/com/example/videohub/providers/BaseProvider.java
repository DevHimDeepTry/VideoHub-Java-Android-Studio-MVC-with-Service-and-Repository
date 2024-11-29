package com.example.videohub.providers;

import android.content.Context;
import java.lang.reflect.InvocationTargetException;

public abstract class BaseProvider {
    // Simple, thread-safe singleton creation
    protected static <T> T provideSingleton(Class<T> serviceClass, Class<? extends T> serviceImplClass, Context context) {
        T instance = null;

        try {
            synchronized (serviceClass) {
                instance = (T) serviceClass.getDeclaredMethod("getInstance", Context.class).invoke(null, context);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return instance;
    }
}
