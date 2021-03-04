package com.example.myapplication.repo;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "user_database")
public class User {

    @NonNull
    @ColumnInfo(name = "user_name")
    private String mUserName;

    @NonNull
    @ColumnInfo(name = "user_department")
    private String mDepartment;

    @Nullable
    @ColumnInfo(name = "user_image")
    private String mUserPicture;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public User(){}
    public User(@NonNull String aName, @NonNull String aDepartment, @Nullable String aProfilePic)
    {
        this.mUserName = aName;
        this.mDepartment = aDepartment;
        this.mUserPicture = aProfilePic;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    @NonNull
    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(@NonNull String aDepartment) {
        this.mDepartment = aDepartment;
    }

    @Nullable
    public String getUserPicture() {
        return mUserPicture;
    }

    public void setUserPicture(@Nullable String aUserPicture) {
        this.mUserPicture = aUserPicture;
    }

    @NonNull
    public String getUserName() {
        return this.mUserName;
    }

    public void setUserName(@NonNull String aUserName) {
        this.mUserName = aUserName;
    }
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  mUserName.equals(user.mUserName) &&
                mDepartment.equals(user.mDepartment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mUserName, mDepartment);
    }
}
