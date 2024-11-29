package com.example.videohub.utils;

import android.text.Editable;
import android.text.TextWatcher;

public class TextWatcherAdapter implements TextWatcher {
    private final Runnable onTextChanged;

    public TextWatcherAdapter(Runnable onTextChanged) {
        this.onTextChanged = onTextChanged;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        onTextChanged.run();
    }

    @Override
    public void afterTextChanged(Editable s) {}
}
