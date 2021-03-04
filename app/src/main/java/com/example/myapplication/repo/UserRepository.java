package com.example.myapplication.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myapplication.database.UserDao;
import com.example.myapplication.database.UserRoomDatabase;

import java.util.List;
import java.util.concurrent.Executors;

public class UserRepository {

    private UserDao mUserDao;
    private LiveData<List<User>> mUserLiveDataList;

    public UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
        mUserLiveDataList = mUserDao.getUsers();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<User>> getAllUsers() {
        return mUserLiveDataList;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(User aUser)
    {
        Executors.newFixedThreadPool(1).execute(() ->
        {
            mUserDao.insert(aUser);
        });
    }

    public void delete(User aUser)
    {
        Executors.newFixedThreadPool(1).execute(() ->
        {
            mUserDao.deleteUser(aUser);
        });
    }
}
