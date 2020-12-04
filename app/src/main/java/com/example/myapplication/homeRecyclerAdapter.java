package com.example.myapplication;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class homeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final int HEADER = 0;
    public static final int CHILD = 1;
    //private ArrayList<String> data;
    //private ArrayList<String> image;
    byte[] byteArray;

    private List<Item> data;

    public homeRecyclerAdapter(List<Item> data)
    {
        this.data = data;
    }
    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
    OnItemClickListener onItemClickListener;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = null;
        switch (viewType) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.homerecycleview, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homerecycleview2, parent, false);
                ListHeaderViewHolder vh = new ListHeaderViewHolder(v);
                return (vh) ;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Item item = data.get(position);
        final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
        //holder.name.setText(data.get(position));
        //byteArray = Base64.decode(image.get(position), Base64.DEFAULT);
        //Bitmap decodedImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        //holder.circleimageview.setImageBitmap(decodedImage);
        //if(position==0)
        //{
            //holder.framelayout.setBackgroundResource(R.drawable.white_circleborder2);
        //}
        switch (item.type) {
            case HEADER:
                itemController.refferalItem = item;
                itemController.name.setText(item.text);
                if(position==0)
                {
                    itemController.framelayout.setBackgroundResource(R.drawable.white_circleborder2);
                }
                byteArray = Base64.decode(item.text2,Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                itemController.circleimageview.setImageBitmap(decodedImage);
                itemController.circleimageview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                        } else {
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            item.invisibleChildren = null;
                        }
                    }
                });
                break;
            case CHILD:
                final ListHeaderViewHolder itemController2 = (ListHeaderViewHolder) holder;
                itemController2.name.setText(data.get(position).text);
                byteArray = Base64.decode(item.text2,Base64.DEFAULT);
                Bitmap decodedImage2 = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                itemController2.circleimageview.setImageBitmap(decodedImage2);
                if(position==1)
                {
                    itemController2.framelayout.setBackgroundResource(R.drawable.white_circleborder2);
                }
                itemController2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(onItemClickListener!=null){
                            onItemClickListener.OnItemClick(position);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public class ListHeaderViewHolder extends RecyclerView.ViewHolder
    {
        public Item refferalItem;
        public TextView name;
        CircleImageView circleimageview;
        FrameLayout framelayout;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.username);
            circleimageview = (CircleImageView) itemView.findViewById(R.id.userpicture);
            framelayout = (FrameLayout) itemView.findViewById(R.id.homepic);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
        }
    }
    /*public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        CircleImageView circleimageview;
        FrameLayout framelayout;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.username);
            circleimageview = (CircleImageView) itemView.findViewById(R.id.userpicture);
            framelayout = (FrameLayout) itemView.findViewById(R.id.homepic);
        }
    }*/

    public static class Item {
        public int type;
        public String text;
        public String text2;
        public List<Item> invisibleChildren;

        public Item() {
        }

        public Item(int type, String text, String text2) {
            this.type = type;
            this.text = text;
            this.text2 = text2;
        }
    }


}
