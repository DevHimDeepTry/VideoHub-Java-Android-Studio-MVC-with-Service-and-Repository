package com.example.videohub.repositories.implement;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.videohub.repositories.interfaces.IBaseRepository;
import com.example.videohub.utils.Callback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.List;

public abstract class BaseRepository<T> implements IBaseRepository<T> {

    protected DatabaseReference databaseReference;

    public BaseRepository(Context context) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://videohubapp-440907-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference(getTableName());
    }

    protected abstract String getTableName();

    @Override
    public void getAll(Callback<List<T>> callback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    callback.onSuccess(Collections.emptyList());
                    return;
                }

                List<T> items = parseData(snapshot);
                callback.onSuccess(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", "Data fetch cancelled: " + error.getMessage());
                callback.onError(error.toException());
            }
        });
    }

    protected abstract List<T> parseData(DataSnapshot snapshot);

    @Override
    public void insert(String key, T item, Callback<Boolean> callback) {
        databaseReference.child(key).setValue(item)
                .addOnSuccessListener(aVoid -> {
                    Log.d("FirebaseDatabase", "Data inserted successfully");
                    callback.onSuccess(true);
                })
                .addOnFailureListener(e -> {
                    Log.e("FirebaseDatabase", "Data insertion failed: " + e.getMessage());
                    callback.onError(new Exception("Insertion failed"));
                });
    }


    @Override
    public void update(String key, T item) {
        databaseReference.child(key).setValue(item)
                .addOnSuccessListener(aVoid -> Log.d("FirebaseDatabase", "Data updated successfully"))
                .addOnFailureListener(e -> Log.e("FirebaseDatabase", "Data update failed: " + e.getMessage()));
    }

    @Override
    public void delete(String key) {
        databaseReference.child(key).removeValue()
                .addOnSuccessListener(aVoid -> Log.d("FirebaseDatabase", "Data deleted successfully"))
                .addOnFailureListener(e -> Log.e("FirebaseDatabase", "Data deletion failed: " + e.getMessage()));
    }

    @Override
    public void getById(String key, Callback<T> callback) {
        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    T item = snapshot.getValue(getModelClass());
                    callback.onSuccess(item);
                } else {
                    callback.onError(new Exception("Data not found for key: " + key));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", "Data fetch cancelled: " + error.getMessage());
                callback.onError(error.toException());
            }
        });
    }

    protected abstract Class<T> getModelClass();
}
