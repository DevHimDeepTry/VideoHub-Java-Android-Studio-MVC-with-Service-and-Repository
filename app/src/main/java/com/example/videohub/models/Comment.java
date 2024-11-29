package com.example.videohub.models;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;

@IgnoreExtraProperties

public class Comment implements Serializable {
    private int id;
    private User user;
    private String message;

    public Comment (){}
    public Comment (
        int id, User user, String message
    ){
        this.id = id;
        this.user = user;
        this.message = message;
    }

    @PropertyName("id")
    public int getId() {
        return id;
    }
    @PropertyName("id")

    public void setId(int id) {
        this.id = id;
    }
    @PropertyName("user")

    public User getUser() {
        return user;
    }
    @PropertyName("user")

    public void setUser(User user) {
        this.user = user;
    }
    @PropertyName("message")

    public String getMessage() {
        return message;
    }
    @PropertyName("message")

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Comment{id=" + id + ", user=" + user + ", message='" + message + "'}";
    }

}
