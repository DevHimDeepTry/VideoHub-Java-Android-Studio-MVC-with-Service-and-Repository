package com.example.videohub.repositories.implement;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.videohub.models.User;
import com.example.videohub.repositories.interfaces.IUserRepository;
import com.example.videohub.utils.Callback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserRepository extends BaseRepository<User> implements IUserRepository {

    public UserRepository(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return "users";
    }

    @Override
    protected List<User> parseData(DataSnapshot snapshot) {
        List<User> userList = new ArrayList<>();
        for (DataSnapshot data : snapshot.getChildren()) {
            User user = data.getValue(User.class);
            if (user != null) {
                userList.add(user);
            }
        }
        return userList;
    }
    @Override
    protected Class<User> getModelClass() {
        return User.class;
    }

    @Override
    public void findByAccountName(String a, Callback<User> callback) {
        databaseReference.orderByChild("account_name").equalTo(a)
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            User user = userSnapshot.getValue(User.class);
                            if (user != null) {
                                callback.onSuccess(user);
                                return;
                            }
                        }
                    }
                    callback.onError(new Exception("User not found"));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    callback.onError(error.toException());
                }
            }
        );
    }
}