package com.example.myapplication.adapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.repo.User;
import com.example.myapplication.viewmodel.UserViewModel;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapter extends ListAdapter<User, UserListAdapter.UserViewHolder>
{
    public boolean mIsPermissionPresent = false;
    public UserListAdapter(@NonNull Context aContext, @NonNull DiffUtil.ItemCallback<User> diffCallback) {
        super(diffCallback);
        if (ContextCompat.checkSelfPermission(aContext,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
        {
            mIsPermissionPresent = true;
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return UserViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User lUser = getItem(position);
        holder.bind(mIsPermissionPresent, lUser);

        holder.mUserDelete.setOnClickListener(view ->
        {
            UserViewModel lUserViewModel = UserViewModel.getInstance();
            lUserViewModel.delete(lUser);
            //notifyItemRemoved(holder.getAdapterPosition());
        });
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView mUserNameTextView;
        private final TextView mUserDepartmentTextView;
        private final CircleImageView mUserPic;
        private final ImageView mUserDelete;

        private UserViewHolder(View itemView)
        {
            super(itemView);
            mUserNameTextView = itemView.findViewById(R.id.user_name);
            mUserDepartmentTextView = itemView.findViewById(R.id.user_department);
            mUserPic = itemView.findViewById(R.id.user_profile_picture);
            mUserDelete = itemView.findViewById(R.id.user_profile_delete);
        }

        public void bind(boolean aIsPermissionPresent, @NonNull User aUser)
        {
            mUserNameTextView.setText(aUser.getUserName());
            mUserDepartmentTextView.setText(aUser.getDepartment());
            if(aUser.getUserPicture() == null || aIsPermissionPresent){
                mUserPic.setImageResource(R.drawable.ic_action_user);
            }else{
                if(new File(aUser.getUserPicture()).exists())
                {
                    mUserPic.setImageURI(Uri.parse(aUser.getUserPicture()));
                }else{
                    mUserPic.setImageResource(R.drawable.ic_action_user);
                }
            }
        }

        public static UserViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_item, parent, false);
            return new UserViewHolder(view);
        }
    }

    @Override
    public int getItemCount()
    {
        if(getCurrentList() == null || getCurrentList().isEmpty()) {
            return 0;
        }else{
            return getCurrentList().size();
        }
    }

    /* Callback **/
    public static class UserDiff extends DiffUtil.ItemCallback<User>
    {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getUserName().equals(newItem.getUserName());
        }
    }
}



