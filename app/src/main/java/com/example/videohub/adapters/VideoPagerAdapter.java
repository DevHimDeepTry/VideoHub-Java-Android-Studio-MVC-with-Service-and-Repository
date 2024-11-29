package com.example.videohub.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videohub.R;
import com.example.videohub.models.Comment;
import com.example.videohub.models.User;
import com.example.videohub.models.Video;

import java.util.List;

public class VideoPagerAdapter extends RecyclerView.Adapter<VideoPagerAdapter.VideoViewHolder> {
    private List<Video> videos;
    private Context context;
    private User currentUser;
    public VideoPagerAdapter(User currentUser,List<Video> videos, Context context) {
        this.videos = videos;
        this.context = context;
        this.currentUser = currentUser;
    }
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view, currentUser);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.bind(video);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VideoViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (!holder.isPlaying && holder.isPrepared) {
            holder.videoView.start();
            holder.playPauseButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VideoViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder.isPlaying) {
            holder.videoView.pause();
            holder.isPlaying = false;
            holder.playPauseButton.setImageResource(R.drawable.ic_play);
        }
    }


    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        private VideoView videoView;
        private SeekBar videoSeekBar;
        private ImageButton playPauseButton;
        private Handler handler = new Handler();
        private boolean isPlaying = false;
        private boolean isPrepared = false;
        private TextView vote, commentCount, shareCount,wishlistCount, ownerName, title;
        private ImageView ownerAvatar, heartIcon, commentIcon;
        private List<Comment> comments;
        private User currentUser;
        public VideoViewHolder(@NonNull View itemView, User currentUser) {
            super(itemView);
            this.currentUser = currentUser;
            videoView = itemView.findViewById(R.id.videoView);
            videoSeekBar = itemView.findViewById(R.id.videoSeekBar);
            playPauseButton = itemView.findViewById(R.id.playPauseButton);
            vote = itemView.findViewById(R.id.heartCount);
            ownerAvatar = itemView.findViewById(R.id.ownerAvatar);
            commentCount = itemView.findViewById(R.id.commentCount);
            shareCount = itemView.findViewById(R.id.shareCount);
            wishlistCount = itemView.findViewById(R.id.wishListCount);
            ownerName = itemView.findViewById(R.id.ownerName);
            title = itemView.findViewById(R.id.title);
            heartIcon = itemView.findViewById(R.id.heartIcon);
            commentIcon = itemView.findViewById(R.id.commentIcon);



            // Set up SeekBar
            videoSeekBar.setMax(100);
            videoSeekBar.setProgress(0);

            videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        int newPosition = (int) (((float) progress / 100) * videoView.getDuration());
                        videoView.seekTo(newPosition);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

            // Handle Play/Pause event
            videoView.setOnClickListener(v -> togglePlayPause());
            playPauseButton.setOnClickListener(v -> togglePlayPause());

            // Handle video completion
            videoView.setOnCompletionListener(mediaPlayer -> {
                videoView.seekTo(0); // Go to the start
                videoView.start();
                isPlaying = true;   // Ensure video stops when completed
                playPauseButton.setVisibility(View.INVISIBLE);
                playPauseButton.setImageResource(R.drawable.ic_pause); // Show play icon
            });


        }
        @SuppressLint("SetTextI18n")
        private void showCommentsDialog(int commentCount) {
            // Tạo dialog và thiết lập layout
            Dialog dialog = new Dialog(itemView.getContext());
            dialog.setContentView(R.layout.dialog_comments);

            // Lấy RecyclerView trong dialog và thiết lập adapter
            RecyclerView commentsRecyclerView = dialog.findViewById(R.id.commentsRecyclerView);
            commentsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

            CommentAdapter adapter = new CommentAdapter(comments, itemView.getContext());
            commentsRecyclerView.setAdapter(adapter);

            // Cập nhật comment count vào TextView
            TextView commentCounts = dialog.findViewById(R.id.commentCounts);
            commentCounts.setText(String.valueOf(commentCount) != null ? commentCount + " Bình Luận" : "0 Bình Luận");

            // Lấy EditText để người dùng nhập bình luận
            EditText commentInput = dialog.findViewById(R.id.commentInput);

            // Thiết lập sự kiện gửi bình luận (Ví dụ: Button "Gửi")
            ImageView sendButton = dialog.findViewById(R.id.sendButton);  // Nếu bạn thêm nút gửi vào layout
            sendButton.setOnClickListener(v -> {
                String comment = commentInput.getText().toString().trim();

                if (!comment.isEmpty()) {
                    // Create a new comment object
                    Comment newComment = new Comment(0, currentUser, comment);

                    // Add the new comment at the beginning of the list
                    comments.add(0, newComment);

                    // Notify the adapter that an item has been inserted at position 0
                    adapter.notifyItemInserted(0);

                    // Increment the comment count and update the TextView
                    int updatedCommentCount = commentCount + 1;
                    commentCounts.setText(updatedCommentCount + " Bình Luận");
                    // Clear the input field after sending the comment
                    commentInput.setText("");
                }
            });

            // Tùy chỉnh độ rộng và chiều cao của dialog
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = (int) (itemView.getContext().getResources().getDisplayMetrics().widthPixels * 1.0); // Độ rộng full màn hình
            params.height = (int) (itemView.getContext().getResources().getDisplayMetrics().heightPixels * 0.6); // Chiều cao màn hình

            // Căn chỉnh dialog xuống dưới cùng của màn hình
            params.gravity = Gravity.BOTTOM;

            // Thiết lập lại các thuộc tính của window
            dialog.getWindow().setAttributes(params);

            // Thiết lập border-radius cho dialog (bo tròn các góc)
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new GradientDrawable() {{
                    setShape(GradientDrawable.RECTANGLE);  // Hình chữ nhật
                    setCornerRadius(50);  // Bo tròn các góc
                    setColor(0xFFFFFFFF);  // Màu nền của dialog (trắng hoặc tùy chỉnh)
                }});
            }

            // Hiển thị dialog
            dialog.show();
        }
        // Toggle Play/Pause for video
        private void togglePlayPause() {
            if (isPrepared) {
                if (isPlaying) {
                    videoView.pause();
                    playPauseButton.setImageResource(R.drawable.ic_play); // Change icon to play
                } else {
                    videoView.start();
                    playPauseButton.setImageResource(R.drawable.ic_pause); // Change icon to pause
                }
                isPlaying = !isPlaying;
                playPauseButton.setVisibility(isPlaying ? View.INVISIBLE : View.VISIBLE);
            }
        }

        // Update SeekBar every 100ms
        private void updateSeekBar() {
            if (videoView != null && isPrepared) {
                int currentPosition = videoView.getCurrentPosition();
                int duration = videoView.getDuration();

                if (duration > 0) {
                    int progress = (int) ((currentPosition / (float) duration) * 100);
                    videoSeekBar.setProgress(progress);
                }

                handler.postDelayed(this::updateSeekBar, 100); // Continue updating every 100ms
            }
        }

        // Bind video URI and prepare video to play
        @SuppressLint("SetTextI18n")
        public void bind(Video video) {
            Uri videoUri = Uri.parse(video.getUri());
            videoView.setVideoURI(videoUri);
            int heartCount = video.getVote_total();
            int share = video.getShare_count();
            int comment = video.getComment_count();
            int wishlist = video.getWishlist_count();

            vote.setText(String.valueOf(heartCount));
            commentCount.setText(String.valueOf(comment));
            shareCount.setText(String.valueOf(share));
            wishlistCount.setText(String.valueOf(wishlist));
            title.setText(video.getTitle());

            this.comments = video.getComments();  // Set comments list from video data

            commentCount.setText(String.valueOf(video.getComment_count()));
            commentIcon.setOnClickListener(v -> showCommentsDialog(comment));

            heartIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isSelected = !v.isSelected();
                    v.setSelected(isSelected);
                    if (isSelected){
                        // Cái này chỉ đang cập nhật giao diện thôi
                        vote.setText(String.valueOf(video.getVote_total()+1));
                    } else {

                        vote.setText(String.valueOf(video.getVote_total()));
                    }
                }
            });
            User user = video.getOwner();

            if (user != null && user.getImage_url() != null) {
                Glide.with(itemView.getContext())
                        .load(user.getImage_url())
                        .into(ownerAvatar);
                ownerName.setText(
                        user.getLast_name()
                        + " "
                        + user.getMiddle_name()
                        + " "
                        + user.getFirst_name()
                        );
            }

            // Set up event when the video is prepared
            videoView.setOnPreparedListener(mediaPlayer -> {
                mediaPlayer.setLooping(false); // Video won't loop
                isPrepared = true;
                if (!isPlaying) {
                    videoView.start();
                    isPlaying = true;
                    playPauseButton.setVisibility(View.INVISIBLE); // Hide play button when video starts
                }
                updateSeekBar(); // Start updating the SeekBar
            });
        }
    }
}
