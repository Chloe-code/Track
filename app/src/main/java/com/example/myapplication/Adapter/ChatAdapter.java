package com.example.myapplication.Adapter;

import android.content.Context;
import android.icu.text.UFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Model.Chat;
import com.example.myapplication.Model.Users;
import com.example.myapplication.R;
import com.journeyapps.barcodescanner.Util;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Chat> mChat;

    public ChatAdapter(Context mContext,ArrayList<Chat> mChat) {
        this.context = mContext;
        this.mChat = mChat;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.message_received, null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
        Log.v("ererereer",mChat.get(position).getSender());
        holder.senderMessage.setText(mChat.get(position).getSender());
        //holder.receiverMessage.setText(mChat.get(position).getReceiver());
        //holder.time.setText(mChat.get(position).getTime());
        //holder.profile_img.setImageResource(R.drawable.ic_people);

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView receiverMessage,senderMessage,time;
        ImageView profile_img;
        ImageView messageSenderPic,messageReceiverPic;

        public ViewHolder(View itemView) {
            super(itemView);
            receiverMessage = itemView.findViewById(R.id.receiverMessage);
            senderMessage = itemView.findViewById(R.id.sender_message);
            time = itemView.findViewById(R.id.time);
            profile_img = itemView.findViewById(R.id.profile_image);

           // messageReceiverPic = itemView.findViewById(R.id.receiver_image);
          //  messageSenderPic = itemView.findViewById(R.id.sender_image);
            time = itemView.findViewById(R.id.time);
            //itemView.setOnClickListener(this);
        }
    }






}

