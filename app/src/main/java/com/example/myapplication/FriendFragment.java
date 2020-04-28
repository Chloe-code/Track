package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FriendFragment extends Fragment
{
    RecyclerView mUserList;

    String s1[], s2[];
    int image[]= {R.drawable.ic_people};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_friend);

        mUserList = findViewById(R.id.fri_list);



        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, image,s1,s2);
        mUserList.setAdapter(myAdapter);
        mUserList.setLayoutManager(new LinearLayoutManager(this));



        }
    }
}
