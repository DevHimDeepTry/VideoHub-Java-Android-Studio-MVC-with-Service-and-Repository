package com.example.videohub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videohub.R;
import com.example.videohub.controllers.AuthController.LoginController;
import com.example.videohub.models.User;
import com.example.videohub.utils.Callback;
import com.example.videohub.utils.TextWatcherAdapter;
import com.example.videohub.utils.Validator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends BaseActivity {

    private LoginController loginController;
    private ProgressBar progressBar;
    private Button loginBtn;
    private TextInputEditText username, password;
    private TextView registerAction, forgotPasswordText;
    private TextInputLayout usernameLayout, passwordLayout;
    private ImageButton google, facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();

        loginController = new LoginController(this);
        progressBar.setVisibility(ProgressBar.GONE);

        // Lắng nghe và xử lý sự kiện login
        listenForLoginEvent();

        // Khởi tạo TextWatchers cho các trường tên người dùng và mật khẩu
        initializeTextWatchers();


        // Lắng nghe và xử lý sự kiện chuyển sang trang đăng ký
        listenNavigateSignUpPageEvent();
    }
    private void initializeTextWatchers(){
        password.addTextChangedListener(new TextWatcherAdapter(() -> {
            Validator.clearErrorOnInputChange(passwordLayout);
        }));
        username.addTextChangedListener(new TextWatcherAdapter(() -> {
            Validator.clearErrorOnInputChange(usernameLayout);
        }));
    }
    private void listenNavigateSignUpPageEvent(){
        registerAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void listenForLoginEvent(){
        loginBtn.setOnClickListener(v -> {
            if (validateInputs()) {
                String u = Objects.requireNonNull(username.getText()).toString().trim();
                String p = Objects.requireNonNull(password.getText()).toString().trim();

                progressBar.setVisibility(ProgressBar.VISIBLE);

                loginController.handleLoginWithSystemAccount(u, p, new Callback<User>() {
                    @Override
                    public void onSuccess(User user) {

                        progressBar.setVisibility(ProgressBar.GONE);
                        if (user != null) {
                            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user", user);
                            intent.putExtras(bundle);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        } else {
                            Validator.handleLoginError(passwordLayout, "Sai tài khoản hoặc mật khẩu");
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        progressBar.setVisibility(ProgressBar.GONE);
                        Log.e("LoginError", "Error: " + e.getMessage());
                        Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private boolean validateInputs() {
        boolean isUsernameValid = Validator.isFieldNotEmpty(usernameLayout, "Vui lòng nhập tài khoản");
        boolean isPasswordValid = Validator.isFieldNotEmpty(passwordLayout, "Vui lòng nhập mật khẩu");
        return isUsernameValid && isPasswordValid;
    }

    private void initializeUI() {
        loginBtn = findViewById(R.id.loginButton);
        google = findViewById(R.id.googleLoginButton);
        facebook = findViewById(R.id.facebookLoginButton);

        username = findViewById(R.id.usernameInput);
        password = findViewById(R.id.passwordInput);

        usernameLayout = findViewById(R.id.usernameLayout);
        passwordLayout = findViewById(R.id.passwordLayout);

        registerAction = findViewById(R.id.signUpText);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);
        progressBar = findViewById(R.id.progressBar);
    }
}