package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment
{
    private Button button, button4, button5, buttont;
    private FloatingActionButton fabbtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        button4 = (Button) getView().findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gopersonaledit = new Intent(getActivity(), personaledit.class);
                startActivity(gopersonaledit);
            }
        });

        button5 = (Button) getView().findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent godevice = new Intent(getActivity(), device.class);
                startActivity(godevice);
            }
        });
        buttont = (Button) getView().findViewById(R.id.buttont);
        buttont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotest2 = new Intent(getActivity(), test2.class);
                startActivity(gotest2);
            }
        });

        button = (Button) getView().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goaddfriend = new Intent(getActivity(), addfriend.class);
                startActivity(goaddfriend);
            }
        });

        /*fabbtn = (FloatingActionButton) getView().findViewById(R.id.fab);
        fabbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), addfriend.class));
            }
        });*/
    }
}
