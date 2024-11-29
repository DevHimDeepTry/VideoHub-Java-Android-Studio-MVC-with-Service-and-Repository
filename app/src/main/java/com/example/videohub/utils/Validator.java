package com.example.videohub.utils;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Validator {

    public static boolean isFieldNotEmpty(TextInputLayout textInputLayout, String errorMessage) {
        String text = Objects.requireNonNull(textInputLayout.getEditText()).getText().toString();
        if (text.isEmpty()) {
            textInputLayout.setError(errorMessage);
            return false;
        }
        textInputLayout.setError(null);
        return true;
    }
    public static void handleLoginError(TextInputLayout passwordLayout, String message) {
        passwordLayout.setError(message);
    }
    public static void clearErrorOnInputChange(TextInputLayout textInputLayout) {
        textInputLayout.setError(null);
    }
}
