package com.example.videohub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.videohub.R;
import com.example.videohub.adapters.VideoAdapterForProfile;
import com.example.videohub.models.User;
import com.example.videohub.models.Video;
import java.util.ArrayList;

public class Profile extends BaseActivity {
    private User currentUser;
    private ArrayList<Video> videoOfUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // User info
        ImageView profileImage = findViewById(R.id.user_profile_image);
        TextView userName = findViewById(R.id.user_name);
        TextView followingCount = findViewById(R.id.following_count);
        TextView followerCount = findViewById(R.id.follower_count);
        TextView likesCount = findViewById(R.id.likes_count);

        // Action buttons
        Button editProfileButton = findViewById(R.id.edit_profile_button);
        Button shareProfileButton = findViewById(R.id.share_profile_button);
        ImageView backButton = findViewById(R.id.back_button);

        // Video list RecyclerView
        RecyclerView videosRecyclerView = findViewById(R.id.videos_recycler_view);

        // Load user data
        readUserAndVideoData();

        // Set user data
        userName.setText(currentUser.getAccount_name());
        followingCount.setText("19");
        followerCount.setText("30");
        likesCount.setText("500");
        Glide.with(this)
                .load(currentUser.getImage_url())
                .into(profileImage);

        // Set up RecyclerView with GridLayoutManager (3 columns)
        videosRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        VideoAdapterForProfile videoAdapter = new VideoAdapterForProfile(this, videoOfUser);
        videosRecyclerView.setAdapter(videoAdapter);

        // Back button action
        backButton.setOnClickListener(v -> backToHome());

        // Edit Profile button action
        editProfileButton.setOnClickListener(v -> {
            // TODO: Handle edit profile
        });

        // Share Profile button action
        shareProfileButton.setOnClickListener(v -> {
            // TODO: Handle share profile
        });
    }
    private void backToHome(){
        Intent intent = new Intent(Profile.this, HomeActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("message", "Back to Home");
        bundle.putSerializable("user", currentUser);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        startActivity(intent);
    }

    private void readUserAndVideoData() {
        Intent intent = getIntent();
        if (intent != null) {
            this.currentUser = (User) intent.getSerializableExtra("user");
            this.videoOfUser = (ArrayList<Video>) intent.getSerializableExtra("videosListOfUser");

            if (currentUser == null) {
                showToast("User data is missing. Loading default profile.");
                // Load default or placeholder profile data if available
                return;
            }
        } else {
            showToast("Failed to load profile data.");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
