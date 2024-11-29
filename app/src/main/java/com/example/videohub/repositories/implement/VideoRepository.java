package com.example.videohub.repositories.implement;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.videohub.models.Comment;
import com.example.videohub.models.Video;
import com.example.videohub.repositories.interfaces.IVideoRepository;
import com.example.videohub.utils.Callback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VideoRepository extends BaseRepository<Video> implements IVideoRepository {
    public VideoRepository(Context context) {
        super(context);
    }
    @Override
    protected String getTableName() {
        return "videos";
    }

    @Override
    protected List<Video> parseData(DataSnapshot snapshot) {
        List<Video> videoList = new ArrayList<>();
        for (DataSnapshot data : snapshot.getChildren()) {
            Video video = data.getValue(Video.class);
            if (video != null) {
                videoList.add(video);
            }
        }
        return videoList;
    }

    @Override
    protected Class<Video> getModelClass() {
        return Video.class;
    }

    @Override
    public void getVideosWithPagination(int limit, int offset, Callback<List<Video>> callback) {
        Query query = databaseReference.orderByKey().limitToFirst(limit).startAt(String.valueOf(offset));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Video> videos = new ArrayList<>();

                for (DataSnapshot videoSnapshot : snapshot.getChildren()) {
                    Video video = videoSnapshot.getValue(Video.class);
                    if (video != null) {
                        // Lấy danh sách comments của video
                        List<Comment> comments = parseComments(videoSnapshot.child("comment"));
                        video.setComments(comments); // Gán danh sách comment cho video
                        videos.add(video);
                    }
                }
                callback.onSuccess(videos); // Trả về danh sách video qua callback
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toException());
            }
        });
    }

    private List<Comment> parseComments(DataSnapshot commentsSnapshot) {
        List<Comment> comments = new ArrayList<>();
        for (DataSnapshot commentSnapshot : commentsSnapshot.getChildren()) {
            Comment comment = commentSnapshot.getValue(Comment.class);
            if (comment != null) {
                comments.add(comment);
            }
        }
        return comments;
    }
}
