package com.example.videohub.activities;

import static com.example.videohub.configs.CloudinaryConfig.initConnectCloudinary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.videohub.R;
import com.example.videohub.adapters.VideoPagerAdapter;
import com.example.videohub.controllers.VideoHubController.CreateVideoController;
import com.example.videohub.controllers.VideoHubController.ListVideosController;
import com.example.videohub.models.User;
import com.example.videohub.models.Video;
import com.example.videohub.utils.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private ViewPager2 videoViewPager;
    private ImageView profileImageView, logoutIcon, createVideoIcon;
    private VideoPagerAdapter videoPagerAdapter;
    private ListVideosController listVideosController;
    private CreateVideoController createVideoController;
    private List<Video> videosList = new ArrayList<>();
    private List<Video> videosListOfUser = new ArrayList<>();

    private boolean isLoading = false;
    private ActivityResultLauncher<Intent> pickVideoLauncher;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setcurrentUser();
        initConnectCloudinary(this);
        initializeUI();
        initializeLaunchers();
        initializeController();
        loadVideos();
        displayProfileImage();
        setupEventListeners();
    }
    private void setcurrentUser(){
        User user = (User) getIntent().getSerializableExtra("user");
        setCurrentUser(user);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    private void initializeController(){
        listVideosController = new ListVideosController(this);
        createVideoController = new CreateVideoController(this);
    }
    private void initializeUI() {
        videoViewPager = findViewById(R.id.videoViewPager);
        profileImageView = findViewById(R.id.profileImageView);
        logoutIcon = findViewById(R.id.logoutIcon);
        createVideoIcon = findViewById(R.id.createVideoIcon);

        videoPagerAdapter = new VideoPagerAdapter(currentUser,videosList, this);
        videoViewPager.setAdapter(videoPagerAdapter);
    }

    private void initializeLaunchers() {
        pickVideoLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri videoUri = result.getData().getData();
                if (videoUri != null) {
                    Log.d("VideoURI", "Video URI: " + videoUri);
                    uploadVideoToCloudinary(videoUri);
                } else {
                    Log.e("HomeActivityError", "Video URI is null.");
                }
            }
        });

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                pickVideoLauncher.launch(new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI));
            } else {
                Toast.makeText(HomeActivity.this, "Permission denied. Cannot select video.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadVideos() {
        listVideosController.loadInitialVideos(new Callback<List<Video>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(List<Video> videos) {
                videosList.addAll(videos);

                videoPagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(HomeActivity.this, "Error loading videos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMoreVideos() {
        if (!isLoading) {
            isLoading = true;
            listVideosController.loadMoreVideos(new Callback<List<Video>>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onSuccess(List<Video> newVideos) {
                    videosList.addAll(newVideos);
                    videoPagerAdapter.notifyDataSetChanged();
                    isLoading = false;
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(HomeActivity.this, "Error loading more videos", Toast.LENGTH_SHORT).show();
                    isLoading = false;
                }
            });
        }
    }

    private void displayProfileImage() {
        User user = getCurrentUser();
        if (user != null) {
            Glide.with(this)
                    .load(user.getImage_url())
                    .into(profileImageView);
        }
    }

    private void setupEventListeners() {
        videoViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == videosList.size() - 3 && !isLoading) {
                    loadMoreVideos();
                }
            }
        });

        logoutIcon.setOnClickListener(v -> {
            new AlertDialog.Builder(HomeActivity.this)
                    .setTitle("Xác nhận đăng xuất")
                    .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
                        finish();
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });

        createVideoIcon.setOnClickListener(v -> initializeVideoPickerAndRequestPermission());

        profileImageView.setOnClickListener(v -> redirectToProfilePage());
    }
    private void redirectToProfilePage() {
        videosListOfUser.clear();
        for (Video video : videosList) {
            if (Objects.equals(video.getUser_id(), currentUser.getId())) {
                videosListOfUser.add(video);
            }
        }
        Intent intent = new Intent(HomeActivity.this, Profile.class);
        intent.putExtra("user", currentUser);
        intent.putExtra("videosListOfUser", (ArrayList<Video>) videosListOfUser);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("LoginError", "check");

        if (requestCode == 1 && resultCode == RESULT_OK) {
            displayProfileImage();
            loadVideos();
        }
    }

    private void initializeVideoPickerAndRequestPermission() {
        pickVideoLauncher.launch(new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI));
    }

    private void uploadVideoToCloudinary(Uri videoUri) {

        String mimeType = getContentResolver().getType(videoUri);
        if (mimeType != null && mimeType.startsWith("video")) {
            listVideosController.uploadVideoToCloudinary(videoUri, new Callback<String>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onSuccess(String videoUrl) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle("Nhập tiêu đề cho video");

                    final EditText input = new EditText(HomeActivity.this);
                    builder.setView(input);

                    builder.setPositiveButton("OK", (dialog, which) -> {
                        String title = input.getText().toString().trim();

                        if (title.isEmpty()) {
                            title = "Title Default";
                        }

                        User user = (User) getIntent().getSerializableExtra("user");
                        Video newVideo = new Video();
                        newVideo.setUri(videoUrl);
                        newVideo.setUser_id(user.getId());
                        newVideo.setVote_total(0);
                        newVideo.setShare_count(0);
                        newVideo.setWishlist_count(0);
                        newVideo.setComment_count(0);
                        newVideo.setTitle(title);
                        newVideo.setOwner(user);
                        newVideo.setComments(null);

                        createVideoController.postVideo(newVideo, new Callback<Boolean>() {
                            @Override
                            public void onSuccess(Boolean result) {
                                if (!result) {
                                    Toast.makeText(HomeActivity.this, "Lỗi kết nối internet!!", Toast.LENGTH_SHORT).show();
                                }
                                videosList.add(0,newVideo);
                                videoPagerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e("LoginError", "Error: " + e.getMessage());
                            }
                        });
                    });

                    builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());

                    builder.show();
                }

                @Override
                public void onError(Exception e) {
                    Log.e("HomeActivityError", "Error uploading video", e);
                    Toast.makeText(HomeActivity.this, "Error uploading video", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Log.e("HomeActivityError", "The selected file is not a valid video.");
            Toast.makeText(HomeActivity.this, "Invalid video file selected.", Toast.LENGTH_SHORT).show();
        }
    }
}
