package com.example.myapplication.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.repo.User;
import com.example.myapplication.viewmodel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddUserDataFragment extends Fragment
{
    public static void moveTo(int aViewId, @NonNull FragmentManager aFragmentManager) {
        Fragment lAddFragment = new AddUserDataFragment();
        FragmentTransaction lRegisterFragTrans = aFragmentManager.beginTransaction();
        lRegisterFragTrans.addToBackStack(
                lAddFragment.getTag()).replace(aViewId,
                lAddFragment);
        lRegisterFragTrans.commit();
    }

    private static final String Tag = "AddUserDataFrag";
    private static final int STORAGE_PERMISSION_CODE = 100;
    private UserViewModel mUserViewModel;
    private CircleImageView mUserImageView;
    private TextInputEditText mUserNameTextView;
    private TextInputEditText mUserDepartmentTextView;
    private  Button mSaveUserDataButton;
    private User mUser;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        Log.d(Tag, "OnCreate Called");
        super.onCreate(savedInstanceState);
        UserViewModel.createInstance(this);
        mUserViewModel = UserViewModel.getInstance();
        mUser = new User();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        Log.d(Tag, "onCreateView Called");
        return inflater.inflate(R.layout.fragment_add_user_data_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View aView, @Nullable Bundle savedInstanceState)
    {
        Log.d(Tag, "onViewCreated Called");
        super.onViewCreated(aView, savedInstanceState);

        Toolbar lToolbar = aView.findViewById(R.id.toolbar);
        lToolbar.setNavigationOnClickListener(view -> getParentFragmentManager().popBackStack());

        mUserImageView = aView.findViewById(R.id.profile_image);
        mUserNameTextView = aView.findViewById(R.id.user_name);
        mUserDepartmentTextView = aView.findViewById(R.id.user_department);
        mSaveUserDataButton = aView.findViewById(R.id.user_save_action);
        mUserNameTextView.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mUserNameTextView, InputMethodManager.SHOW_IMPLICIT);

        FloatingActionButton lEditProfileFab = aView.findViewById(R.id.user_profile_picture_edit);

        mUserNameTextView.addTextChangedListener (new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2){
                checkRequiredFields();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mUserDepartmentTextView.addTextChangedListener (new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2){
                checkRequiredFields();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        lEditProfileFab.setOnClickListener(view ->
        {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                Log.w(Tag, "Permissions: Manifest.permission.READ_EXTERNAL_STORAGE Exists");
                // If activity resumes check for storage permission
                // If permission granted and Storages null init them
                Intent lSelectPicture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(lSelectPicture, 1);
            }else{
                ActivityCompat
                        .requestPermissions(
                                getActivity(),
                                new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                STORAGE_PERMISSION_CODE);
            }
        });

        mSaveUserDataButton.setOnClickListener(view ->
        {
            mUser.setUserName(mUserNameTextView.getText().toString());
            mUser.setDepartment(mUserDepartmentTextView.getText().toString());
            mUserViewModel.insert(mUser);
            getParentFragmentManager().popBackStack();
        });
    }

    private void checkRequiredFields()
    {
        mSaveUserDataButton.setEnabled(!mUserNameTextView.getText().toString().isEmpty() &&
                !mUserDepartmentTextView.getText().toString().isEmpty());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE)
        {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent takePicture = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(takePicture, 1);

            } else {
                Toast.makeText(getContext(),
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_CANCELED)
        {
            switch (requestCode)
            {
                case 1:
                    if (resultCode == RESULT_OK && data != null)
                    {
                        Log.d(Tag, "onActivityResult RESULT_OK");
                        Uri lSelectedImage =  data.getData();
                        Log.d(Tag, "onActivityResult lSelectedImageUri : "+lSelectedImage);
                        String[] lFilePathColumn = {MediaStore.Images.Media.DATA};
                        if (lSelectedImage != null)
                        {
                            Log.d(Tag, "onActivityResult lSelectedImage != null ; "+lSelectedImage);
                            Cursor lCursor = getContext().getContentResolver().query(lSelectedImage,
                                    lFilePathColumn, null, null, null);
                            if (lCursor != null) {
                                lCursor.moveToFirst();
                                int columnIndex = lCursor.getColumnIndex(lFilePathColumn[0]);
                                String lPicturePath = lCursor.getString(columnIndex);
                                mUser.setUserPicture(lPicturePath);
                                mUserImageView.setImageBitmap(BitmapFactory.decodeFile(mUser.getUserPicture()));
                                lCursor.close();
                            }
                        }else{
                            Log.d(Tag, "onActivityResult getContentResolver == null");
                            mUser.setUserPicture(null);
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        UserViewModel.getInstance().clearall();

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mUserNameTextView.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
