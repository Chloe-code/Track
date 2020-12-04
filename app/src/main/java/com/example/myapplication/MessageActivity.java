package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.myapplication.Adapter.ChatAdapter;
import com.example.myapplication.Adapter.fri_RecyclerViewAdapter;
import com.example.myapplication.Model.Chat;
import com.example.myapplication.Model.Users;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Thread.sleep;


public class MessageActivity extends AppCompatActivity {
    CircleImageView profile_image;
    TextView username;
    ImageButton photo,camera;
    ImageButton btn_send;
    EditText text_send;
    ArrayList<Chat> mChat;
    RecyclerView mChatList;
    Intent intent;
    ChatAdapter adapterChat;
    private String[] line2 = null;
    Chat chat ;
    View view;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        mChat = new ArrayList<>();
        chat = new Chat();
        initData();

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.chat_name);
        intent = getIntent();
        //String sendername = intent.getStringExtra("Name");

        text_send = findViewById(R.id.messagetext6);
        btn_send = findViewById(R.id.sent);
        btn_send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //String msg = text_send.getText().toString();
                Log.v("erfg","okokokok");
                new Thread(){
                    public void run() {
                        String line = ws_test2.messageinsert(text_send.getText().toString());
                        if (Integer.parseInt(line)==1) {
                            text_send.setText(null);
                        }
                    }
                }.start();
            }
        });


        Button btn = findViewById(R.id.fri_set);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetFriend();
            }
        });

        photo = findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED){
                    }
                }
            }
        });
        handler.post(task);
    }

   public void openSetFriend(){
       Intent intent = new Intent(this, SetFriend.class);
       startActivity(intent);

   }
    private void mChatList(){
        mChatList = findViewById(R.id.recyclerview_chat);
        mChatList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapterChat = new ChatAdapter(this,mChat);
        mChatList.setAdapter(adapterChat);
    }

    private Runnable task =new Runnable() {
        public void run() {
            //initData();
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mChatList();
        }
    };
    private void initData()
    {
        new Thread(){
            public void run() {
                String[] line = ws_test2.message("Apple");
                if (line != null) {
                    for (int i = 0; i < line.length; i++) {
                        chat = new Chat();
                        String[] split_line = line[i].split("%");
                        Log.v("wewewe","jijijij "+split_line[2]+":"+split_line[3]);
                        chat.setSender(split_line[1]);
                        chat.setTime(split_line[2]+":"+split_line[3]);
                        mChat.add(chat);
                    }
                }
            }
        }.start();
    }
}
