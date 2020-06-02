package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class homeRecyclerAdapter extends RecyclerView.Adapter<homeRecyclerAdapter.ViewHolder>
{
    private ArrayList<String> data;
    private ArrayList<String> image;
    byte[] byteArray;

    public homeRecyclerAdapter(FragmentActivity activity, ArrayList<String> data,ArrayList<String> image)
    {
        this.data = data;
        this.image = image;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homerecycleview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.name.setText(data.get(position));
        byteArray = Base64.decode(image.get(position), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        holder.circleimageview.setImageBitmap(decodedImage);
    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        CircleImageView circleimageview;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.username);
            circleimageview = (CircleImageView) itemView.findViewById(R.id.userpicture);
        }
    }
}
