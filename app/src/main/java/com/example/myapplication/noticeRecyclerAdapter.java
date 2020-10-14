package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class noticeRecyclerAdapter extends RecyclerView.Adapter<noticeRecyclerAdapter.ViewHolder>
{
    private ArrayList<String> name,pic,email;
    private Context context;
    byte[] byteArray;

    public noticeRecyclerAdapter(Context context, ArrayList<String> requestname, ArrayList<String> requestemail, ArrayList<String> requestpic) {
        this.name = requestname;
        this.email = requestemail;
        this.pic = requestpic;
        Log.v("test","catch");
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.noticerecycler,null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleTv.setText(name.get(position));
        byteArray = Base64.decode(pic.get(position), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        holder.picture.setImageBitmap(decodedImage);
    }

    @Override
    public int getItemCount() {
        return pic.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTv;
        public CircleImageView picture;
        public Button addbutton;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.requestusername);
            picture = (CircleImageView) itemView.findViewById(R.id.requestpic);
            addbutton = (Button) itemView.findViewById(R.id.requestcheck);
            addbutton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View view) {
                    final String requestmail = email.get(getAdapterPosition());
                    Thread thread = new Thread(){
                        public void run() {
                            String line = ws_test2.acceptrequest("Apple@gmail.com",requestmail,1,"Apple@gmail.com");
                            if (line.equals("1")==true) {

                            }
                        }
                    };
                    thread.start();
                }
            });
        }
    }
}
