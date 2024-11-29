package com.example.videohub.controllers.VideoHubController;

import android.content.Context;
import android.net.Uri;

import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.videohub.models.Video;
import com.example.videohub.providers.BusinessServiceProvider;
import com.example.videohub.services.interfaces.ICloudinaryService;
import com.example.videohub.services.interfaces.IVideoService;
import com.example.videohub.utils.Callback;

import java.util.List;
import java.util.Map;

public class ListVideosController {
//    Chức năng: Pagination và lọc danh sách video để trả về cho người dùng theo logic như tiktok. Mình sẽ tải trước 5 video randome và khi nguoi dung luot xuong video thu 3 thi minh bắt đầu goi tiếp để trả về thêm 5 video khac de tang hieu suat trai nghiem nguoi dung xem video.
    private final IVideoService videoService;
    private final ICloudinaryService cloudinaryService;
    private int currentOffset = 0;
    private static final int PAGE_SIZE = 5;
    public ListVideosController(Context context) {
        this.videoService = BusinessServiceProvider.provideVideoService(context);
        this.cloudinaryService = BusinessServiceProvider.provideCloudinaryServices(context);
    }

    // Load initial set of videos
    public void loadInitialVideos(Callback<List<Video>> callback) {
        currentOffset = 0; // Reset offset for initial load
        videoService.fetchVideos(PAGE_SIZE, currentOffset, callback);
        currentOffset += PAGE_SIZE;
    }

    // Load more videos when user scrolls near the end
    public void loadMoreVideos(Callback<List<Video>> callback) {
        videoService.fetchVideos(PAGE_SIZE, currentOffset, callback);
        currentOffset += PAGE_SIZE;
    }

    public void uploadVideoToCloudinary(Uri videoUri, Callback<String> callback) {
        cloudinaryService.uploadVideoFileToCloudinary(videoUri, new UploadCallback() {
            @Override
            public void onStart(String requestId) {}

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {}

            @Override
            public void onSuccess(String requestId, Map resultData) {
                String videoUrl = resultData.get("url").toString();
                if (!videoUrl.startsWith("https://")) {
                    videoUrl = videoUrl.replace("http://", "https://");
                }
                callback.onSuccess(videoUrl);
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                callback.onError(new Exception(error.getDescription()));
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {}
        });
    }
}
