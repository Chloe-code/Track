package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.myapplication.Adapter.ChatAdapter;
import com.example.myapplication.Adapter.fri_RecyclerViewAdapter;
import com.example.myapplication.Model.Chat;
import com.example.myapplication.Model.Users;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Thread.sleep;


public class MessageActivity extends AppCompatActivity {
    CircleImageView profile_image;
    TextView username;
    ImageView img;
    ImageButton photo,camera,btn_send;
    EditText text_send;
    ArrayList<Chat> mChat;
    RecyclerView mChatList;
    Intent intent;
    ChatAdapter adapterChat;
    private String[] line2 = null;
    Chat chat ;
    private Handler handler = new Handler();

    private static final int REQUEST_CODE_TAKE_PHOTO = 0;
    private static final int CAMERA_PERMISSION_CODE = 00;
    private static final int REQUEST_CODE_CHOOSE_IMAGE = 1;
    private static final int GALLERY_PERMISSION_CODE = 10;
    private Uri img_uri;


    String[]cameraPermissions;
    String[]storagePermissions;





    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        mChat = new ArrayList<>();
        chat = new Chat();
        initData();
        //init permissions arrays
        cameraPermissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.chat_name);
        img = findViewById(R.id.msg_pic);
        text_send = findViewById(R.id.messagetext6);
        btn_send = findViewById(R.id.sent);
//        ImageButton set = findViewById(R.id.fri_set);
        camera = findViewById(R.id.camera);

        intent = getIntent();



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

//        set.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openSetFriend();
//            }
//        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraPermission();
            }
        });
        handler.post(task);
        photo = findViewById(R.id.gallery);
        Log.v("photo2","jfdfljglkgjdgilj");
//        photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.v("photo","jfdfljglkgjdgilj");
//                Toast.makeText(MessageActivity.this,"photo",Toast.LENGTH_SHORT).show();
//            }
//        });
    }
    private void pickImgFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CODE_CHOOSE_IMAGE);
    }
    private void CameraPermission() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},REQUEST_CODE_TAKE_PHOTO);
            }else {
            openCamera();
        }
    }

    public void openSetFriend(){
       Intent intent = new Intent(this, SetFriend.class);
       startActivity(intent);

   }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==CAMERA_PERMISSION_CODE){
            if(grantResults.length<0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            openCamera();
            }else {
                Toast.makeText(this,"Camera Permission is required",Toast.LENGTH_SHORT).show();

            }
        }
    }
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CODE_TAKE_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                if (resultCode == RESULT_OK) {

        if(requestCode==REQUEST_CODE_CHOOSE_IMAGE){

            img_uri = data.getData();
            try {
                sendImageMassage(img_uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == REQUEST_CODE_TAKE_PHOTO)
            {
                try {
                    sendImageMassage(img_uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
                super.onActivityResult(requestCode,resultCode,data);
    }

    private void sendImageMassage(Uri img_uri) throws IOException {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("sending img...");
        progressDialog.show();

        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),img_uri);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100 ,baos);
        byte[] data = baos.toByteArray();

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
                sleep(10000);
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
                        chat.setSender(split_line[0]);
                        chat.setReceiver(split_line[1]);
                        chat.setMessage(split_line[2]);
                        chat.setTime(split_line[3]+":"+split_line[4]);
                        chat.setProfileImg(split_line[5]);
                        mChat.add(chat);
                    }
                }
            }
        }.start();
    }
}
