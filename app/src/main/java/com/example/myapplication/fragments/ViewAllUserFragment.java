package com.example.myapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.UserListAdapter;
import com.example.myapplication.viewmodel.UserViewModel;

public class ViewAllUserFragment extends Fragment
{
    public static void moveTo(int aViewId, @NonNull FragmentManager aFragmentManager)
    {
        Fragment lAllUserFrag = new ViewAllUserFragment();
        FragmentTransaction lRegisterFragTrans = aFragmentManager.beginTransaction();
        lRegisterFragTrans.addToBackStack(
                lAllUserFrag.getTag()).replace(aViewId,
                lAllUserFrag);
        lRegisterFragTrans.commit();
    }
    private static final String Tag = "ViewAllUserFrag";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        Log.d(Tag, "OnCreate Called");
        super.onCreate(savedInstanceState);
        UserViewModel.createInstance(this);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        Log.d(Tag, "onCreateView Called");
        return inflater.inflate(R.layout.fragment_view_all_user_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View aView, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(aView, savedInstanceState);

        Toolbar lToolbar = aView.findViewById(R.id.toolbar);
        lToolbar.setNavigationOnClickListener(view -> getParentFragmentManager().popBackStack());

        RecyclerView lRecyclerView = aView.findViewById(R.id.activity_main_userdata_recyclerview);

        DividerItemDecoration itemDecorator =
                new DividerItemDecoration(aView.getContext(), DividerItemDecoration.VERTICAL);
        lRecyclerView.addItemDecoration(itemDecorator);

        UserListAdapter lUserAdapter = new UserListAdapter(
                aView.getContext(),
                new UserListAdapter.UserDiff());
        lRecyclerView.setLayoutManager(new LinearLayoutManager(aView.getContext()));
        lRecyclerView.setAdapter(lUserAdapter);

        UserViewModel.getInstance().getAllUserList().observe(getActivity(), lUserAdapter::submitList);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        UserViewModel.getInstance().clearall();
    }
}
