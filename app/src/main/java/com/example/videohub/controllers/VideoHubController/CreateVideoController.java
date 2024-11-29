package com.example.videohub.controllers.VideoHubController;

import android.content.Context;

import com.example.videohub.models.Video;
import com.example.videohub.providers.BusinessServiceProvider;
import com.example.videohub.services.interfaces.IVideoService;
import com.example.videohub.utils.Callback;

public class CreateVideoController {
    private final IVideoService videoService;
    public CreateVideoController(Context context){
        this.videoService = BusinessServiceProvider.provideVideoService(context);
    }
    public void postVideo(Video video, Callback<Boolean> callback){
        videoService.createNewVideo(video,callback);
    }
}
