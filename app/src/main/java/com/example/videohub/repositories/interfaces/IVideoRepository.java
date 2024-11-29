package com.example.videohub.repositories.interfaces;

import com.example.videohub.models.Video;
import com.example.videohub.utils.Callback;

import java.util.List;

public interface IVideoRepository extends IBaseRepository<Video>{
    void getVideosWithPagination(int limit, int offset, Callback<List<Video>> callback);

}
