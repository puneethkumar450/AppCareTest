package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.repo.User;
import com.example.myapplication.repo.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel
{
    public  UserRepository mRepository;
    private final LiveData<List<User>> mUserList;
    private static UserViewModel mViewModel = null;

    public static void createInstance(@NonNull Fragment aFragment)
    {
        mViewModel = new ViewModelProvider(aFragment).get(UserViewModel.class);
    }

    public static UserViewModel getInstance()
    {
        return mViewModel;
    }

    public UserViewModel(Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mUserList = mRepository.getAllUsers();
    }

    public LiveData<List<User>> getAllUserList() {
        return mUserList;
    }

    public void insert(@NonNull User aUser) {
        mRepository.insert(aUser);
    }

    public void delete(@NonNull User aUser) {
        mRepository.delete(aUser);
    }

    public void clearall() {
        mViewModel = null;
        mRepository = null;
    }
}