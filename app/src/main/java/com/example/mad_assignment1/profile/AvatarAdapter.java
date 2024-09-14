package com.example.mad_assignment1.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mad_assignment1.R;

import java.util.List;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.ViewHolder> {

    private final List<Integer> avatarList; // List of avatar resource IDs
    private final Context context;
    private OnAvatarSelectedListener listener;
    private int selectedAvatarResourceId;

    public interface OnAvatarSelectedListener {
        void onAvatarSelected(int avatarResourceId);
    }

    public void setOnAvatarSelectedListener(OnAvatarSelectedListener listener) {
        this.listener = listener;
    }

    public AvatarAdapter(Context context, List<Integer> avatars, int selectedAvatarResourceId) {
        this.context = context;
        this.avatarList = avatars;
        this.selectedAvatarResourceId = selectedAvatarResourceId;
    }

    public void setSelectedAvatarResourceId(int avatarResourceId) {
        this.selectedAvatarResourceId = avatarResourceId;
        notifyDataSetChanged();
    }

    @Override
    public AvatarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_avatar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AvatarAdapter.ViewHolder holder, int position) {
        int avatarResourceId = avatarList.get(position);
        holder.avatarImageView.setImageResource(avatarResourceId);

        // Highlight the selected avatar
        if (avatarResourceId == selectedAvatarResourceId) {
            holder.avatarItemContainer.setBackgroundResource(R.drawable.avatar_selected_border);
        } else {
            holder.avatarItemContainer.setBackgroundResource(0); // Remove background
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAvatarSelected(avatarResourceId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return avatarList.size();
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatarImageView;
        public FrameLayout avatarItemContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatar_image_view);
            avatarItemContainer = itemView.findViewById(R.id.avatar_item_container);
        }
    }

}
