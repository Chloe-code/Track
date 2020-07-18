package com.example.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class mapinfowindow implements GoogleMap.InfoWindowAdapter
{
    byte[] byteArray;
    private Activity context;
    private ArrayList<String> data;
    private View view;

    public mapinfowindow(Activity context, ArrayList<String> data)
    {
        this.context = context;
        view = context.getLayoutInflater().inflate(R.layout.activity_mapinfowindow, null);
        this.data = data;
    }

    @Override
    public View getInfoWindow(Marker marker) {

        return null;
    }

    @Override
    public View getInfoContents(Marker marker)
    {
        TextView infodevice = (TextView) view.findViewById(R.id.devicetext);
        ImageView infogender = (ImageView) view.findViewById(R.id.gendertext);
        TextView infobirth = (TextView) view.findViewById((R.id.birthtext));
        TextView infoname = (TextView) view.findViewById(R.id.usernametext);
        TextView infolocation = (TextView) view.findViewById(R.id.locationtext);
        TextView infointro = (TextView) view.findViewById(R.id.notetext);
        CircleImageView circleimageview = (CircleImageView) view.findViewById(R.id.userpicture);
        infolocation.setText(data.get(6));
        infodevice.setText(data.get(0));
        infobirth.setText(data.get(1));
        if(data.get(2).equals("女")==true)
        { infogender.setBackgroundResource(R.drawable.ic_female); }
        else { infogender.setBackgroundResource(R.drawable.ic_male);}
        infoname.setText(data.get(3));
        infointro.setText(data.get(4));
        byteArray = Base64.decode(data.get(5), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        circleimageview.setImageBitmap(decodedImage);
        return view;
    }
    private class Snippet {
    }

}
