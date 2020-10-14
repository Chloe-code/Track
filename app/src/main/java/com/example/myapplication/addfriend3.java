package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class addfriend3 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> resultname ,resultpic, resultemail;
    private addfriendRecyclerAdapter addfriendrecyclerAdapter;
    private Handler handler = new Handler();
    private EditText entername;
    private SearchView searchname;
    private ImageView imageback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend3);
        resultname = new ArrayList<>();
        resultpic = new ArrayList<>();
        resultemail = new ArrayList<>();
        initData();
        handler.postDelayed(task,500);
        entername = (EditText) findViewById(R.id.editText6);
        searchname = (SearchView) findViewById(R.id.friendsearch);
        imageback = (ImageView) findViewById(R.id.imageback);
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }
    private void initData()
    {
        Bundle bundle = getIntent().getExtras();
        final String username = bundle.getString("username");
        new Thread() {
            @Override
            public void run() {
                String[] dline = ws_test2.addfriendlist(username);
                if (dline!=null) {
                    for(int i=0;i<dline.length;i++) {
                        resultname.add(username);
                        String[] split_line = dline[i].split("%");
                        resultemail.add(split_line[0]);
                        resultpic.add(split_line[1]);
                    }
                }
            }
        }.start();
    }
    private void RecyclerView()
    {
        recyclerView=(RecyclerView)findViewById(R.id.addfriendlist);
        addfriendrecyclerAdapter = new addfriendRecyclerAdapter(getApplicationContext(),resultname,resultemail,resultpic);
        recyclerView.setAdapter(addfriendrecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
    private Runnable task =new Runnable() {
        public void run() {
            RecyclerView();
        }
    };
}
