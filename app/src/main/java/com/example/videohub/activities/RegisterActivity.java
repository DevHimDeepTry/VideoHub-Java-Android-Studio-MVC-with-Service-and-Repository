package com.example.videohub.activities;

import static com.example.videohub.configs.CloudinaryConfig.initConnectCloudinary;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;

import com.example.videohub.R;
import com.example.videohub.controllers.AuthController.RegisterController;
import com.example.videohub.models.User;
import com.example.videohub.utils.ActivityResultUtils;
import com.example.videohub.utils.Callback;
import com.example.videohub.utils.TextWatcherAdapter;
import com.example.videohub.utils.Validator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.UUID;

public class RegisterActivity extends BaseActivity {
    private RegisterController registerController;
    private TextInputEditText imageUrlInput, firstNameInput, middleNameInput, lastNameInput, accountNameInput, emailInput, passwordInput, ageInput;
    private TextInputLayout firstNameLayout, middleNameLayout, lastNameLayout, accountNameLayout, emailLayout, passwordLayout, ageLayout, imageUrlLayout;
    private TextView signInText;
    private Button registerButton, uploadImageButton;
    private ActivityResultLauncher<Intent> pickImageLauncher;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeUI();
        registerController = new RegisterController(this);

        initConnectCloudinary(this);

        initializeImagePickerAndRequestPermission();

        launchImagePicker();

        setupRegisterButton();
        setupSignInText();
    }

    private void initializeUI() {
        firstNameInput = findViewById(R.id.firstNameInput);
        middleNameInput = findViewById(R.id.middleNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        accountNameInput = findViewById(R.id.accountNameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passInput);
        ageInput = findViewById(R.id.ageInput);
        imageUrlInput = findViewById(R.id.imageUrlInput);

        firstNameLayout = findViewById(R.id.firstNameLayout);
        middleNameLayout = findViewById(R.id.middleNameLayout);
        lastNameLayout = findViewById(R.id.lastNameLayout);
        accountNameLayout = findViewById(R.id.accountNameLayout);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passLayout);
        ageLayout = findViewById(R.id.ageLayout);
        imageUrlLayout = findViewById(R.id.imageUrlLayout);

        uploadImageButton = findViewById(R.id.uploadImageButton);
        registerButton = findViewById(R.id.registerButton);
        signInText = findViewById(R.id.signInText);

        // Clear error when the user starts typing
        firstNameInput.addTextChangedListener(new TextWatcherAdapter(() -> Validator.clearErrorOnInputChange(firstNameLayout)));
        lastNameInput.addTextChangedListener(new TextWatcherAdapter(() -> Validator.clearErrorOnInputChange(lastNameLayout)));
        emailInput.addTextChangedListener(new TextWatcherAdapter(() -> Validator.clearErrorOnInputChange(emailLayout)));
        accountNameInput.addTextChangedListener(new TextWatcherAdapter(() -> Validator.clearErrorOnInputChange(accountNameLayout)));
        passwordInput.addTextChangedListener(new TextWatcherAdapter(() -> Validator.clearErrorOnInputChange(passwordLayout)));
    }
    private void launchImagePicker(){
        uploadImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });
    }
    private void initializeImagePickerAndRequestPermission(){
        pickImageLauncher = ActivityResultUtils.createImagePickerLauncher(this, this::uploadToCloudinary);
        requestPermissionLauncher = ActivityResultUtils.createPermissionRequestLauncher(this, Manifest.permission.READ_EXTERNAL_STORAGE,
                new ActivityResultUtils.PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {
                        Toast.makeText(RegisterActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(RegisterActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void uploadToCloudinary(Uri imageUri) {

        registerController.uploadImageToCloudinary(imageUri, new Callback<String>() {
            @Override
            public void onSuccess(String imageUrl) {
                Log.d("ImageURI", "Video URI: " + imageUrl);

                imageUrlInput.setText(imageUrl);
                Toast.makeText(RegisterActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(RegisterActivity.this, "Error uploading image: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupRegisterButton() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()){
                    User user = new User();
                    user.setId(UUID.randomUUID().toString());
                    user.setFirst_name(Objects.requireNonNull(firstNameInput.getText()).toString().trim());
                    user.setMiddle_name(Objects.requireNonNull(middleNameInput.getText()).toString().trim());
                    user.setLast_name(Objects.requireNonNull(lastNameInput.getText()).toString().trim());
                    user.setAccount_name(Objects.requireNonNull(accountNameInput.getText()).toString().trim());
                    user.setEmail(Objects.requireNonNull(emailInput.getText()).toString().trim());
                    user.setPassword(Objects.requireNonNull(passwordInput.getText()).toString().trim());
                    user.setAge(Integer.parseInt(Objects.requireNonNull(ageInput.getText()).toString().trim()));
                    user.setImage_url(Objects.requireNonNull(imageUrlInput.getText()).toString().trim());
                    user.setIs_active(true);

                    registerController.registerSystemAccount(user, new Callback<Boolean>() {
                        @Override
                        public void onSuccess(Boolean result) {
                            if (result) {
                                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    private boolean validateInputs(){
        boolean isFirstNameValid = Validator.isFieldNotEmpty(firstNameLayout, "Tên là bắt buộc");
        boolean isLastNameValid = Validator.isFieldNotEmpty(lastNameLayout, "Họ là bắt buộc");
        boolean isEmailValid = Validator.isFieldNotEmpty(emailLayout, "Email là bắt buộc");
        boolean isAccountNameValid = Validator.isFieldNotEmpty(accountNameLayout, "Tên tài khoản là bắt buộc");
        boolean isPasswordValid = Validator.isFieldNotEmpty(passwordLayout, "Mật khẩu là bắt buộc");
        return isFirstNameValid
                && isLastNameValid
                && isEmailValid
                && isAccountNameValid
                && isPasswordValid;
    }
    private void setupSignInText() {
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
