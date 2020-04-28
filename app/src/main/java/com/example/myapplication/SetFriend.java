package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class SetFriend extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendset);

        Spinner sp = (Spinner)findViewById(R.id.fri_spinner);
        final String[] friend = {"好友", "摯友"};
        ArrayAdapter<String> friendList = new ArrayAdapter<>(SetFriend.this,android.R.layout.simple_spinner_dropdown_item,friend);
        sp.setAdapter(friendList);

        TextView dlt = findViewById(R.id.fri_delete);
        dlt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SetFriend.this)
                        .setIcon(R.drawable.ic_alert)
                        .setTitle("確定要刪除好友嗎?")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("取消",null).create()
                        .show();
            }
            });
    }












    }
