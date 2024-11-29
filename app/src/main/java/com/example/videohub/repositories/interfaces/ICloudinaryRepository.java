package com.example.videohub.repositories.interfaces;

import android.net.Uri;

import com.cloudinary.android.callback.UploadCallback;

public interface ICloudinaryRepository {
    void uploadImageFileToCloudinary(Uri imageUri, String folderName, UploadCallback callback);
    void uploadVideoFileToCloudinary(Uri imageUri, String folderName, UploadCallback callback);
}
