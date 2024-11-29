package com.example.videohub.models;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;

@IgnoreExtraProperties
public class User implements Serializable {
    private String id;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String account_name;
    private String email;
    private String password;
    private String image_url;
    private String  day_of_birth;
    private int age;
    private Long  created_at;
    private Long  updated_at;
    private Long  deleted_at;
    private boolean is_active;

    public User() {}

    // Constructor with parameters
    public User( String first_name, String middle_name, String last_name, String account_name,
                String email,int age,String day_of_birth, String password, String image_url,boolean is_active) {
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.account_name = account_name;
        this.email = email;
        this.password = password;
        this.image_url = image_url;
        this.day_of_birth = day_of_birth;
        this.age = age;
        this.is_active = is_active;
    }

    @PropertyName("first_name")
    public String getFirst_name() {
        return first_name;
    }

    @PropertyName("first_name")
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @PropertyName("middle_name")
    public String getMiddle_name() {
        return middle_name;
    }

    @PropertyName("middle_name")
    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    @PropertyName("last_name")
    public String getLast_name() {
        return last_name;
    }

    @PropertyName("last_name")
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @PropertyName("account_name")
    public String getAccount_name() {
        return account_name;
    }

    @PropertyName("account_name")
    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    @PropertyName("email")
    public String getEmail() {
        return email;
    }

    @PropertyName("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @PropertyName("password")
    public String getPassword() {
        return password;
    }

    @PropertyName("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @PropertyName("image_url")
    public String getImage_url() {
        return image_url;
    }

    @PropertyName("image_url")
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @PropertyName("age")
    public int getAge() {
        return age;
    }

    @PropertyName("age")
    public void setAge(int age) {
        this.age = age;
    }

    @PropertyName("is_active")
    public boolean getIs_active() {
        return is_active;
    }

    @PropertyName("is_active")
    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    @PropertyName("id")
    public String getId(){
        return id;
    }
    @PropertyName("id")
    public void setId(String key){
        this.id = key;
    }

    @Override
    public String toString() {
        return
                "\nName: " + first_name + " " + (middle_name != null ? middle_name + " " : "") + last_name +
                "\nAccount Name: " + account_name +
                "\nEmail: " + email +
                "\nAge: " + age +
                "\nActive: " + (is_active ? "Yes" : "No");
    }

}
