package com.example.videohub.models;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import java.io.Serializable;
import java.util.List;

@IgnoreExtraProperties
public class Video implements Serializable {
    private String id;
    private String uri;
    private String user_id;
    private int vote_total;
    private User owner;
    private int share_count;
    private int wishlist_count;
    private int comment_count;
    private List<Comment> comment;
    private String title;

    public Video() {}

    public Video(
            String id,
            String uri,
            String user_id,
            int vote_total,
            User owner,
            int share_count,
            int wishlist_count,
            int comment_count,
            String title,
            List<Comment> comment
    ){
        this.id = id;
        this.uri = uri;
        this.user_id = user_id;
        this.vote_total = vote_total;
        this.owner = owner;
        this.share_count = share_count;
        this.wishlist_count = wishlist_count;
        this.comment_count = comment_count;
        this.title = title;
        this.comment = comment;
    }

    @PropertyName("title")
    public String getTitle() {
        return title;
    }

    @PropertyName("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @PropertyName("comments")
    public List<Comment> getComments() {
        return comment;
    }

    @PropertyName("comments")
    public void setComments(List<Comment> comment) {
        this.comment = comment;
    }

    @PropertyName("comment_count")
    public int getComment_count() {
        if (comment != null) {
            return comment.size();
        }
        return 0;
    }

    @PropertyName("comment_count")
    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    @PropertyName("share_count")
    public int getShare_count() {
        return share_count;
    }

    @PropertyName("share_count")
    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    @PropertyName("wishlist_count")
    public int getWishlist_count() {
        return wishlist_count;
    }

    @PropertyName("wishlist_count")
    public void setWishlist_count(int wishlist_count) {
        this.wishlist_count = wishlist_count;
    }

    @PropertyName("owner")
    public User getOwner() {
        return owner;
    }

    @PropertyName("owner")
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @PropertyName("id")
    public String getId() {
        return id;
    }

    @PropertyName("id")
    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("uri")
    public String getUri() {
        return uri;
    }

    @PropertyName("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    @PropertyName("user_id")
    public String getUser_id() {
        return user_id;
    }

    @PropertyName("user_id")
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @PropertyName("vote_total")
    public int getVote_total() {
        return vote_total;
    }

    @PropertyName("vote_total")
    public void setVote_total(int vote_total) {
        this.vote_total = vote_total;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", uri='" + uri + '\'' +
                ", user_id='" + user_id + '\'' +
                ", vote_total=" + vote_total +
                ", owner=" + owner +
                ", share_count=" + share_count +
                ", wishlist_count=" + wishlist_count +
                ", comment_count=" + comment_count +
                ", comments=" + comment.toString() +
                ", title='" + title + '\'' +
                '}';
    }
}
