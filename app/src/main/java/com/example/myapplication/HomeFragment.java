package com.example.myapplication;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

public class HomeFragment extends Fragment
{
    private View view;
    private Button button2, button5;
    private ImageButton imageButton2;
    private EditText editText;
    SupportMapFragment supportMapFragment;
    private RecyclerView recyclerView;
    private homeRecyclerAdapter homerecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    //ArrayList<String> title,photo;
    List<homeRecyclerAdapter.Item> title;
    private Handler handler = new Handler();
    private String line0=null, line00=null, devicelist=null;
    private String line1=null, line10=null;
    private String line2=null;
    private FloatingActionButton fab;
    private LinearLayout addfriendlayout,layoutContent;
    private RelativeLayout layoutMain;
    private boolean isOpen = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home,container, false);
        //homere();
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (getActivity().getCurrentFocus() != null && getActivity().getCurrentFocus().getWindowToken() != null) {
                        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                return false;
            }
        });
        fab = (FloatingActionButton) view.findViewById(R.id.button);
        handler.post(task);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        /*button4 = (Button) getView().findViewById(R.id.button4);
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
        });*/
        /*button = (Button) getView().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goaddfriend = new Intent(getActivity(), addfriend.class);
                startActivity(goaddfriend);
            }
        });*/

        layoutMain = (RelativeLayout) getView().findViewById(R.id.layoutMain);
        addfriendlayout = (LinearLayout) getView().findViewById(R.id.addfriendlayout);
        layoutContent = (LinearLayout) getView().findViewById(R.id.friendlistlayout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewaddfriend();
            }
        });
        button5 = (Button) getView().findViewById(R.id.button5);
        button2 = (Button) getView().findViewById(R.id.button2);
        imageButton2 = (ImageButton) getView().findViewById(R.id.imageButton2);
        editText = (EditText) getView().findViewById(R.id.editText6);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goqrcode = new Intent(getActivity(),qrcode.class);
                startActivity(goqrcode);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goqrcode = new Intent(getActivity(),qrcode.class);
                startActivity(goqrcode);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gosearch = new Intent(getActivity(),addfriend3.class);
                Bundle bundle = new Bundle();
                bundle.putString("username",editText.getText().toString());
                gosearch.putExtras(bundle);
                startActivity(gosearch);
            }
        });

    }
    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(23.90699, 121.53048);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I Am Here.");
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        googleMap.addMarker(markerOptions);
    }*/
    /*private void homere()
    {
        new Thread() {
            public void run() {
                line1 = ws_test2.homerecyclrview("Apple");
                if (line1.equals("error") == false) {
                    String[] split_line = line1.split("%");
                    for (int i = 0; i < split_line.length; i++) {
                        line2 = ws_test2.personinfoselect(split_line[i]);
                        if (line2.equals("error") == false) {
                            //String[] split_line2=null;
                            String[] split_line2 = line2.split("%");
                            title.add("okok" + split_line2[0]);
                            photo.add(split_line2[6]);
                        }
                    }
                }
            }
        }.start();
    }*/

    @Override
    public void onStart() {
        super.onStart();
        //initData();
        //RecyclerView();
    }

    private void initData()
    {
        new Thread() {
            public void run() {
                line0 = ws_test2.personinfoselect("Apple@gmail.com");
                devicelist = ws_test2.homerecyclrview2("Apple@gmail.com");
                line00 = ws_test2.deviceinfoselect(devicelist);
                if (line0.equals("error")==false) {
                    String[] split_line0 = line0.split("%");
                    String[] split_line00 = line00.split("%");
                    title.add(new homeRecyclerAdapter.Item(homeRecyclerAdapter.HEADER, split_line0[0].replaceAll("\\s",""),split_line0[6]));
                    title.add(new homeRecyclerAdapter.Item(homeRecyclerAdapter.CHILD, split_line00[0].replaceAll("\\s",""),split_line00[7]));
                }
                String[] line1 = ws_test2.homerecyclrview("Apple@gmail.com");
                if (line1!= null) {
                    for (int i = 0; i < line1.length; i++) {
                        line2 = ws_test2.personinfoselect(line1[i]);
                        devicelist = ws_test2.homerecyclrview2(line1[i]);
                        line10 = ws_test2.deviceinfoselect(devicelist);
                        if (line2.equals("error") == false) {
                            String[] split_line2 = line2.split("%");
                            title.add(new homeRecyclerAdapter.Item(homeRecyclerAdapter.HEADER, split_line2[0].replaceAll("\\s",""),split_line2[6]));
                            if(line10.equals("error")==false)
                            {
                                String[] split_line22 = line10.split("%");
                                title.add(new homeRecyclerAdapter.Item(homeRecyclerAdapter.CHILD, split_line22[0].replaceAll("\\s",""),split_line22[7]));
                            }
                        }
                    }
                }
            }
        }.start();
        /*new Thread() {
            public void run() {
                line1 = ws_test2.homerecyclrview("Apple");
                if (line1.equals("error") == false) {
                    String[] split_line = line1.split("%");
                    for (int i = 0; i < split_line.length; i++) {
                        line2 = ws_test2.personinfoselect(split_line[i]);
                        if (line2.equals("error") == false) {
                            //String[] split_line2=null;
                            String[] split_line2 = line2.split("%");
                            title.add(split_line2[0].replaceAll("\\s+", ""));
                            photo.add(split_line2[6]);
                        }
                    }
                }
            }
        }.start();*/
    }
    private void RecyclerView()
    {
        //獲取RecyclerView
        title = new ArrayList<>();
        //photo = new ArrayList<>();
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        //創建adapter
        //homerecyclerAdapter = new homeRecyclerAdapter(getActivity(),title,photo);
        //給RecyclerView設置adapter
        //recyclerView.setAdapter(homerecyclerAdapter);
        //設置layoutManager,可以設置顯示效果，是線性布局、grid布局，還是瀑布流布局
        //參數是：上下文、列表方向（横向還是縱向）、是否倒敘
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        homeRecyclerAdapter adapter = new homeRecyclerAdapter(title);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new homeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Toast.makeText(getActivity(), "here", Toast.LENGTH_SHORT).show();
            }
        });
        //RecyclerView中没有item的監聽事件，需要自己在適配器中寫一個監聽事件的接口。參數根據自定義
        /*homerecyclerAdapter.setOnItemClickListener(new homerecycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, GoodsEntity data) {
                //此處進行監聽事件的業務處理
                Toast.makeText(getActivity(),"我是item",Toast.LENGTH_SHORT).show();
            }
        });*/
    }
    private Runnable task =new Runnable() {
        public void run() {
            //handler.postDelayed(this,5*1000);
            initData();
            RecyclerView();
        }
    };
    private void viewaddfriend() {

        if (!isOpen) {

            int x = layoutContent.getRight();
            int y = layoutContent.getBottom();

            int startRadius = 0;
            int endRadius = (int) Math.hypot(layoutMain.getWidth(), layoutMain.getHeight());

            fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), android.R.color.white, null)));
            fab.setImageResource(R.drawable.ic_close);

            Animator anim = ViewAnimationUtils.createCircularReveal(addfriendlayout, x, y, startRadius, endRadius);

            addfriendlayout.setVisibility(View.VISIBLE);
            anim.start();

            isOpen = true;

        } else {

            int x = addfriendlayout.getRight();
            int y = addfriendlayout.getBottom();

            int startRadius = Math.max(layoutContent.getWidth(), layoutContent.getHeight());
            int endRadius = 0;

            fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), android.R.color.white, null)));
            fab.setImageResource(R.drawable.ic_add2);

            Animator anim = ViewAnimationUtils.createCircularReveal(addfriendlayout, x, y, startRadius, endRadius);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    addfriendlayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator)
                { }

                @Override
                public void onAnimationRepeat(Animator animator)
                { }
            });
            anim.start();

            isOpen = false;
        }
    }
}
