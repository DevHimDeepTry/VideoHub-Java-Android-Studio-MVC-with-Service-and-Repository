package com.example.videohub.services.interfaces;

import com.example.videohub.models.Video;
import com.example.videohub.utils.Callback;

import java.util.List;

public interface IVideoService {
    void fetchVideos(int limit, int offset, Callback<List<Video>> callback);
    void createNewVideo(Video video,  Callback<Boolean> callback);
}
