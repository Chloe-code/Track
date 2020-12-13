package com.example.myapplication.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.UFormat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.Model.Chat;
import com.example.myapplication.Model.Users;
import com.example.myapplication.R;
import com.example.myapplication.ws_test2;
import com.journeyapps.barcodescanner.Util;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Chat> mChat;
    public final int MSG_TYPE_LEFT = 0;
    public final int MSG_TYPE_RIGHT = 1;
    private String[] line = null;
    byte[] byteArray;

    public ChatAdapter(Context mContext, ArrayList<Chat> mChat) {
        this.mChat = mChat;
        this.context = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.message, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
        Log.v("ererereer", mChat.get(position).getSender());
        if(getItemViewType(position)==0)
        {
            holder.rightMsg.setText(mChat.get(position).getMessage());
            holder.rightTime.setText(mChat.get(position).getTime());
            holder.left.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.leftMsg.setText(mChat.get(position).getMessage());
            holder.leftTime.setText(mChat.get(position).getTime());
            byteArray = Base64.decode(mChat.get(position).getProfileImg(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            holder.friendimg.setImageBitmap(decodedImage);
            holder.right.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemViewType(final int position) {

        if(mChat.get(position).getSender().equals("Apple")){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView leftTime,rightTime,leftMsg,rightMsg;
        CircleImageView friendimg;
        LinearLayout left;
        RelativeLayout right;

        public ViewHolder(View itemView) {
            super(itemView);
            leftMsg = itemView.findViewById(R.id.lefttext);
            rightMsg = itemView.findViewById(R.id.righttext);
            leftTime = itemView.findViewById(R.id.lefttime);
            rightTime =itemView.findViewById(R.id.righttime);
            friendimg = itemView.findViewById(R.id.friendimg);
            right = itemView.findViewById(R.id.rightlayout);
            left = itemView.findViewById(R.id.leftlayout);
        }
    }
}




