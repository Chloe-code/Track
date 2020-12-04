package com.example.myapplication.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.MessageActivity;
import com.example.myapplication.R;
import com.example.myapplication.Model.Users;
import com.example.myapplication.Adapter.fri_RecyclerViewAdapter;
import com.example.myapplication.homeRecyclerAdapter;
import com.example.myapplication.ws_test2;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static java.lang.Thread.sleep;

public class FriendFragment extends Fragment {
    private RecyclerView mUserList;
    private View view;
    private fri_RecyclerViewAdapter adapterUsers;
    private ArrayList line = null;
    private String[] line2 = null;
    private String line1 = null;
    //private ArrayList<String> name ,status ;
    private ArrayList<Users> friendlist;
    private Handler handler = new Handler();
    Users users ;
    Button button;
    Context context;


    /*private static final String NAMESPACE = "http://tempuri.org/";       //WebService預設的命名空間
    private static final String METHOD_NAME = "FriendList";
    private  static final String SOAP_ACTION = " http://tempuri.org/FriendList";          //命名空間+要用的函數名稱
    private static final String URL = "http://localhost:13715/WebService.asmx?WSDL";*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.fragment_friend,container,false);

        friendlist = new ArrayList<>();
        users = new Users();
        handler.post(task);
        return view;
    }



    private void initData()
    {
        new Thread() {
            @Override
            public void run() {
                line2 = ws_test2.friend_list("Apple");
                Log.v("test3","goooooodName");
                if (line2!=null)
                {
                    for(int i=0;i<line2.length;i++)
                    {
                        users = new Users();
                        String[] split_line = line2[i].split("%");
                        users.setName(split_line[0]);
                        users.setStatus(split_line[1]);
                        friendlist.add(users);
                    }
                }
            }
        }.start();

    }
    private void mUserList(){
        mUserList=(RecyclerView)view.findViewById(R.id.users_list);
        mUserList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        adapterUsers = new fri_RecyclerViewAdapter(getContext(),friendlist);
        mUserList.setAdapter(adapterUsers);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //name = (TextView) getView().findViewById(R.id.textView3);
        //btn = (Button) getView().findViewById(R.id.button3);

    }

    private Runnable task =new Runnable() {
        public void run() {
            initData();
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mUserList();
        }
    };
}











