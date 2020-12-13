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
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class addfriendRecyclerAdapter extends RecyclerView.Adapter<addfriendRecyclerAdapter.ViewHolder>
{
    private ArrayList<String> name,pic,email;
    private Context context;
    byte[] byteArray;
    GoogleSignInAccount alreadyloggedAccount;

    public addfriendRecyclerAdapter(Context context, ArrayList<String> friendname,ArrayList<String> friendemail,ArrayList<String> friendpic) {
        this.name = friendname;
        this.email = friendemail;
        this.pic = friendpic;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.addfriendlist,null);
        alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(context);
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
            titleTv = (TextView) itemView.findViewById(R.id.searchusername);
            picture = (CircleImageView) itemView.findViewById(R.id.friendpic);
            addbutton = (Button) itemView.findViewById(R.id.addfriendcheck2);
            addbutton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View view) {
                    View toastView = LayoutInflater.from(view.getContext()).inflate(R.layout.addfriendtoast, null);
                    Toast toast = new Toast(view.getContext());
                    toast.setView(toastView);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0,0);
                    toast.show();
                    final String friendmail = email.get(getAdapterPosition());
                    Thread thread = new Thread(){
                        public void run() {
                            String line = ws_test2.addfriend(alreadyloggedAccount.getEmail(),friendmail,0,alreadyloggedAccount.getEmail());
                            if (line.equals("1")==true) {

                            }
                        }
                    };
                    thread.start();
                    Intent gohome = new Intent(context,MainActivity.class);
                    gohome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(gohome);
                }
            });
        }
    }
}
