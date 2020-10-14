package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class beaconRecyclerAdapter extends RecyclerView.Adapter<beaconRecyclerAdapter.ViewHolder>
{
    private ArrayList<String> name,pic,uuid;
    private ArrayList<Integer> beacon;
    private Context context;
    byte[] byteArray;
    String distance = null;
    private Handler handler = new Handler();

    public beaconRecyclerAdapter(Context context, ArrayList<String> devicename, ArrayList<String> deviceuuid, ArrayList<String> devicepic, ArrayList<Integer> devicebeacon) {
        this.name = devicename;
        this.uuid = deviceuuid;
        this.pic = devicepic;
        this.beacon = devicebeacon;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.beaconadapter,null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleTv.setText(name.get(position));
        byteArray = Base64.decode(pic.get(position), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        holder.picture.setImageBitmap(decodedImage);
        if(beacon.get(position)==1)
        {
            holder.open.setChecked(true);
        }
        else { holder.open.setChecked(false); }
    }

    @Override
    public int getItemCount() {
        return pic.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTv;
        public CircleImageView picture;
        public Switch open;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.devicename);
            picture = (CircleImageView) itemView.findViewById(R.id.devicepic);
            open = (Switch) itemView.findViewById(R.id.switch1);
            open.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked())
                    {
                        final String deviceuuid = uuid.get(getAdapterPosition());
                        initData(deviceuuid);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                new Thread(){
                                    public void run() {
                                        String line = ws_test2.beaconcondition(deviceuuid,1);
                                        if (line.equals("1")==true) {

                                        }
                                    }
                                }.start();
                                Intent intent = new Intent("beacon_open");
                                intent.putExtra("device_beacon_open",deviceuuid);
                                intent.putExtra("device_distance",distance);
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            }
                        }, 500);
                    }
                    else
                    {
                        final String deviceuuid = uuid.get(getAdapterPosition());
                        initData(deviceuuid);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                new Thread(){
                                    public void run() {
                                        String line = ws_test2.beaconcondition(deviceuuid,0);
                                        if (line.equals("1")==true) {

                                        }
                                    }
                                }.start();
                            }
                        }, 500);
                    }
                }
            });
        }
    }
    private void initData(final String deviceuuid)
    {
        new Thread(){
            public void run() {
                String line = ws_test2.deviceinfoselect(deviceuuid);
                if (line!=null) {
                    String[] split_line = line.split("%");
                    distance = split_line[5];
                }
            }
        }.start();
    }
}
