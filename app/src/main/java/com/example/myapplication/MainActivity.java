package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.fragments.AddUserDataFragment;
import com.example.myapplication.fragments.ViewAllUserFragment;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LoadViews.....
        ExtendedFloatingActionButton lAddUserFab = findViewById(R.id.activity_main_add_user_data_button);
        ExtendedFloatingActionButton lSeeUserDataFab = findViewById(R.id.activity_main_see_user_data_button);
        lAddUserFab.setOnClickListener(view ->
        {
            //Move to frag.....
            AddUserDataFragment.moveTo(android.R.id.content, getSupportFragmentManager());
        });

        lSeeUserDataFab.setOnClickListener(view ->
        {
            //Move to frag.....
            ViewAllUserFragment.moveTo(android.R.id.content, getSupportFragmentManager());
        });
    }
}