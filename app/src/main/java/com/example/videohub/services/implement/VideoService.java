package com.example.videohub.services.implement;

import com.example.videohub.models.Video;
import com.example.videohub.repositories.interfaces.IVideoRepository;
import com.example.videohub.services.interfaces.IVideoService;
import com.example.videohub.utils.Callback;

import java.util.List;
import java.util.UUID;

public class VideoService implements IVideoService {
    private final IVideoRepository videoRepository;

    public VideoService(IVideoRepository videoRepository){
        this.videoRepository = videoRepository;
    }

    @Override
    public void fetchVideos(int limit, int offset, Callback<List<Video>> callback) {
        videoRepository.getVideosWithPagination(limit, offset, callback);
    }

    @Override
    public void createNewVideo(Video video, Callback<Boolean> callback){
        String randomValue = UUID.randomUUID().toString();
        videoRepository.insert(randomValue,video, callback);
    }
}
