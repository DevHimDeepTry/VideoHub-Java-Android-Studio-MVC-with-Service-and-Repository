package com.example.videohub.adapters;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.videohub.R;
import com.example.videohub.models.Video;

import java.util.ArrayList;

public class VideoAdapterForProfile extends RecyclerView.Adapter<VideoAdapterForProfile.VideoViewHolder> {
    private final Context context;
    private final ArrayList<Video> videoList;

    public VideoAdapterForProfile(Context context, ArrayList<Video> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_for_profile, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videoList.get(position);

        // Load video thumbnail into ImageView
        Glide.with(context)
                .load(video.getUri()) // Assuming `getUri` returns the video URL or URI
                .into(holder.videoThumbnail);

        // Set an onClickListener to show video in a dialog
        holder.videoThumbnail.setOnClickListener(v -> {
            // Create and configure dialog
            Dialog videoDialog = new Dialog(context);
            videoDialog.setContentView(R.layout.dialog_video); // Define dialog_video layout
            videoDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            // Set up VideoView inside dialog
            VideoView videoView = videoDialog.findViewById(R.id.dialog_video_view);
            videoView.setVideoURI(Uri.parse(video.getUri())); // Set the video URI

            // Start playing video automatically
            videoView.setOnPreparedListener(mediaPlayer -> mediaPlayer.start());

            // Display dialog
            videoDialog.show();
        });
    }


    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView videoThumbnail;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.video_thumbnail);
        }
    }
}
