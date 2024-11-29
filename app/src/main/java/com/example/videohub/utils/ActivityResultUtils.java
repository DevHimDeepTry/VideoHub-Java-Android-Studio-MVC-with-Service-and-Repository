package com.example.videohub.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class ActivityResultUtils {

    // Launch image picker
    public static ActivityResultLauncher<Intent> createImagePickerLauncher(
            AppCompatActivity activity, ImagePickerCallback callback) {
        return activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        callback.onImagePicked(selectedImageUri);
                    }
                }
        );
    }

    // Launch video picker
    public static ActivityResultLauncher<Intent> createMediaPickerLauncher(
            AppCompatActivity activity, MediaPickerCallback callback) {
        return activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri mediaUri = result.getData().getData();
                        callback.onMediaPicked(mediaUri);
                    }
                }
        );
    }

    public static ActivityResultLauncher<String> createPermissionRequestLauncher(
            AppCompatActivity activity, String permission, PermissionCallback callback) {
        return activity.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        callback.onPermissionGranted();
                    } else {
                        callback.onPermissionDenied();
                    }
                }
        );
    }

    // Callback interface for image picking
    public interface ImagePickerCallback {
        void onImagePicked(Uri imageUri);
    }

    // Callback interface for video picking
    public interface MediaPickerCallback {
        void onMediaPicked(Uri mediaUri);
    }

    // Callback interface for permission handling
    public interface PermissionCallback {
        void onPermissionGranted();
        void onPermissionDenied();
    }
}
