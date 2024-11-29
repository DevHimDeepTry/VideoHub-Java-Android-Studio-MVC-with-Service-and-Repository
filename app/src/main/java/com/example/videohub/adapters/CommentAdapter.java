package com.example.videohub.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videohub.R;
import com.example.videohub.models.Comment;

import java.util.List;
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> comments;
    private Context context;

    public CommentAdapter(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        private TextView commentText, commenterName;
        private ImageView commenterAvatar, iconButton;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentText = itemView.findViewById(R.id.commentContent);
            commenterName = itemView.findViewById(R.id.commentUserName);
            commenterAvatar = itemView.findViewById(R.id.commentUserAvatar);
            iconButton = itemView.findViewById(R.id.iconButton);
        }

        public void bind(Comment comment) {
            commentText.setText(comment.getMessage());
            commenterName.setText(comment.getUser().getAccount_name());
            Glide.with(itemView.getContext()).load(comment.getUser().getImage_url()).into(commenterAvatar);
            
        }
    }
}
