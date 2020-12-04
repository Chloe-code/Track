package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.noticeRecyclerAdapter;
import com.example.myapplication.ws_test2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class NoticeFragment extends Fragment
{
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<String> resultname ,resultpic, resultemail;
    private noticeRecyclerAdapter noticerecyclerAdapter;
    private Handler handler = new Handler();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notice,container, false);
        resultname = new ArrayList<>();
        resultemail = new ArrayList<>();
        resultpic = new ArrayList<>();
        handler.post(task);
        return view;
    }

    private void initData()
    {
        new Thread() {
            @Override
            public void run() {
                String[] dline = ws_test2.noticerequest("Apple@gmail.com");
                if (dline!=null) {
                    for(int i=0;i<dline.length;i++) {
                        String[] split_line = dline[i].split("%");
                        resultname.add(split_line[0]);
                        resultemail.add(split_line[1]);
                        resultpic.add(split_line[2]);
                    }
                }
            }
        }.start();
    }
    private void RecyclerView()
    {
        //獲取RecyclerView
        recyclerView=(RecyclerView)view.findViewById(R.id.noticelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        noticerecyclerAdapter = new noticeRecyclerAdapter(getContext(),resultname,resultemail,resultpic);
        recyclerView.setAdapter(noticerecyclerAdapter);
    }
    private Runnable task =new Runnable() {
        public void run() {
            initData();
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            RecyclerView();
        }
    };
}
