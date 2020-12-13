package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;

public class beaconopen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> resultname ,resultpic, resultuuid;
    private ArrayList<Integer> resultbeacon;
    private beaconRecyclerAdapter beaconrecyclerAdapter;
    private Handler handler = new Handler();
    private ImageView imageback;
    int pp=1;
    GoogleSignInAccount googlesigninaccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beaconopen);
        resultname = new ArrayList<>();
        resultpic = new ArrayList<>();
        resultuuid = new ArrayList<>();
        resultbeacon = new ArrayList<>();
        initData();
        handler.postDelayed(task,500);
        imageback = (ImageView) findViewById(R.id.beaconlistback);
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData()
    {
        new Thread() {
            @Override
            public void run() {
                String dline = ws_test2.homerecyclrview2(googlesigninaccount.getEmail());//"Apple@gmail.com"
                String dline2 = ws_test2.deviceinfoselect(dline);
                int line = ws_test2.beaconcheck(dline);
                if (dline2!=null) {
                    String[] split_line = dline2.split("%");
                    resultuuid.add(split_line[2]);
                    resultname.add(split_line[0]);
                    resultpic.add(split_line[7]);
                    resultbeacon.add(line);
                }
            }
        }.start();
    }

    private void RecyclerView()
    {
        recyclerView=(RecyclerView)findViewById(R.id.devicebeacon);
        beaconrecyclerAdapter = new beaconRecyclerAdapter(getApplicationContext(),resultname,resultuuid,resultpic,resultbeacon);
        recyclerView.setAdapter(beaconrecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
    }
    private Runnable task =new Runnable() {
        public void run() {
            RecyclerView();
        }
    };
}
