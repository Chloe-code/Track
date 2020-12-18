package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

public class HistoryFragment extends Fragment
{
    TextView start, end;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_history,container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        start = (TextView) getView().findViewById(R.id.start);
        end = (TextView)getView().findViewById(R.id.end2);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Datedia(getActivity(), new Datedia.DateCallBack() {
                    @Override
                    public void onClick(String date) {
                        start.setText(date);
                    }
                });
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Datedia(getActivity(), new Datedia.DateCallBack() {
                    @Override
                    public void onClick(String date) {
                        end.setText(date);
                    }
                });
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map2);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                LatLng latLng = new LatLng(23.976259, 121.604963);
                Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                        .add(
                                new LatLng(23.976106, 121.604821),
                                new LatLng(23.976178, 121.604769),
                                new LatLng(23.976259, 121.604705),
                                new LatLng(23.976315, 121.604632),
                                new LatLng(23.976409, 121.604645),
                                new LatLng(23.976464, 121.604744),
                                new LatLng(23.976456, 121.604819),
                                new LatLng(23.976393, 121.604861),
                                new LatLng(23.976308, 121.604923),
                                new LatLng(23.976259, 121.604963))
                .width(3)
                .color(Color.RED));
                googleMap.addMarker(new MarkerOptions().position(latLng));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
            }
        });
    }
}
