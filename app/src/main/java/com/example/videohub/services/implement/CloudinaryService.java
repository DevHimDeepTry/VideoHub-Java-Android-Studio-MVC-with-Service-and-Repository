package com.example.videohub.services.implement;

import android.net.Uri;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.UploadCallback;
import com.example.videohub.repositories.interfaces.ICloudinaryRepository;
import com.example.videohub.services.interfaces.ICloudinaryService;

public class CloudinaryService implements ICloudinaryService {
    private final ICloudinaryRepository cloudinaryRepository;

    public CloudinaryService(ICloudinaryRepository cloudinaryRepository){
        this.cloudinaryRepository = cloudinaryRepository;
    }

    @Override
    public void uploadImageFileToCloudinary(Uri imageUri, UploadCallback callback) {
        String folderName = "VideoHubAndroidStudio";
        cloudinaryRepository.uploadImageFileToCloudinary(imageUri, folderName, callback);
    }
    @Override
    public void uploadVideoFileToCloudinary(Uri imageUri, UploadCallback callback) {
        String folderName = "VideoHubAndroidStudio";
        cloudinaryRepository.uploadVideoFileToCloudinary(imageUri, folderName, callback);
    }
}
