package com.example.myapplication.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.repo.User;

import java.util.List;

@Dao
public interface UserDao
{
    @Query("SELECT * FROM user_database ORDER BY id ASC")
    LiveData<List<User>> getUsers();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User aUser);

    @Query("DELETE FROM user_database")
    void deleteAll();

    @Delete
    void deleteUser(User aUser);
}
