package com.example.videohub.repositories.implement;

import android.content.Context;
import android.net.Uri;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.UploadCallback;
import com.example.videohub.repositories.interfaces.ICloudinaryRepository;

public class CloudinaryRepository implements ICloudinaryRepository {
    private Context context;

    public CloudinaryRepository(Context context){
        this.context = context;
    }

    @Override
    public void uploadImageFileToCloudinary(Uri imageUri, String folderName, UploadCallback callback) {
        MediaManager.get().upload(imageUri)
                .option("folder", folderName)
                .callback(callback)
                .dispatch();
    }
    @Override
    public void uploadVideoFileToCloudinary(Uri imageUri, String folderName, UploadCallback callback) {
        MediaManager.get().upload(imageUri)
                .option("folder", folderName)
                .option("resource_type", "video")
                .option("chunk_size", 6000000)
                .callback(callback)
                .dispatch();
    }
}
