package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.example.myapplication.MessageActivity;
import com.example.myapplication.R;
import com.example.myapplication.Model.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class fri_RecyclerViewAdapter extends RecyclerView.Adapter<fri_RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Users> friendlist;
    private Context context;
    String theLastMessage;
    byte[] byteArray;

    //FragmentActivity activity
    public fri_RecyclerViewAdapter(Context context, ArrayList<Users> friendlist) {
        this.friendlist = friendlist;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.users_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.v("tttttttttt",friendlist.get(position).getUserimg());
        holder.Name.setText(friendlist.get(position).getName());
        holder.Status.setText(friendlist.get(position).getStatus());
        byteArray = Base64.decode(friendlist.get(position).getUserimg(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        holder.Img.setImageBitmap(decodedImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MessageActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return friendlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Name, Status;
        ImageView Img;

        public ViewHolder(final View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.txt_name);
            Status = itemView.findViewById(R.id.txt_message);
            Img = itemView.findViewById(R.id.img1); }
    }
    private void lastMessage (String username, TextView Last_msg){
        theLastMessage = "default";


    }
}













