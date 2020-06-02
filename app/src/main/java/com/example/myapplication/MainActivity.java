package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.ls.LSException;

public class MainActivity extends AppCompatActivity {
    //implements OnMapReadyCallback
    Toolbar toolbar;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    DrawerLayout drawerLayout;
    ImageButton menuRight;
    private Fragment mhomefragment=null;
    private Fragment mfriendfragment=null;
    private Fragment mhistoryfragment=null;
    private Fragment mnoticefragment=null;
    public MarkerOptions markerOptions2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navlistener);
        NavigationView drawernav = findViewById(R.id.setview);
        Menu menu = drawernav.getMenu();
        MenuItem persontitle= menu.findItem(R.id.persontitle);
        MenuItem devicetitle= menu.findItem(R.id.devicetitle);
        SpannableString s1 = new SpannableString(persontitle.getTitle());
        SpannableString s2 = new SpannableString(devicetitle.getTitle());
        s1.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s1.length(), 0);
        s2.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s2.length(), 0);
        persontitle.setTitle(s1);
        devicetitle.setTitle(s2);
        drawernav.setNavigationItemSelectedListener(listenerset);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle Toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(Toggle);
        Toggle.isDrawerIndicatorEnabled();
        Toggle.syncState();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.END))
                { drawerLayout.closeDrawer(GravityCompat.END); }
                else { drawerLayout.openDrawer(GravityCompat.END); }
            }
        });
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        mhomefragment=new HomeFragment();
        mfriendfragment=new FriendFragment();
        mhistoryfragment=new HistoryFragment();
        mnoticefragment=new NoticeFragment();
        setDefaultFragment();
    }
    private void getCurrentLocation() {
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if (location != null) {
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(final GoogleMap googleMap) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I Am Here.");
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                            googleMap.setMyLocationEnabled(true);
                            //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            googleMap.addMarker(markerOptions2);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navlistener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            FragmentManager fragmentManager = getFragmentManager();
            switch (item.getItemId()) {
                case R.id.action_home:
                    getSupportFragmentManager().beginTransaction().remove(mfriendfragment).commit();
                    getSupportFragmentManager().beginTransaction().remove(mhistoryfragment).commit();
                    getSupportFragmentManager().beginTransaction().remove(mnoticefragment).commit();
                    if (!mhomefragment.isAdded()) {
                        getSupportFragmentManager().beginTransaction().add(R.id.google_map, mhomefragment).commit();
                    }
                    break;
                case R.id.action_friend:
                    getSupportFragmentManager().beginTransaction().remove(mhomefragment).commit();
                    getSupportFragmentManager().beginTransaction().remove(mhistoryfragment).commit();
                    getSupportFragmentManager().beginTransaction().remove(mnoticefragment).commit();
                    if (!mfriendfragment.isAdded()) {
                        getSupportFragmentManager().beginTransaction().add(R.id.google_map, mfriendfragment).commit();
                    }
                    break;
                case R.id.action_history:
                    getSupportFragmentManager().beginTransaction().remove(mhomefragment).commit();
                    getSupportFragmentManager().beginTransaction().remove(mfriendfragment).commit();
                    getSupportFragmentManager().beginTransaction().remove(mnoticefragment).commit();
                    if (!mhistoryfragment.isAdded()) {
                        getSupportFragmentManager().beginTransaction().add(R.id.google_map, mhistoryfragment).commit();
                    }
                    break;
                case R.id.action_notice:
                    getSupportFragmentManager().beginTransaction().remove(mhomefragment).commit();
                    getSupportFragmentManager().beginTransaction().remove(mhistoryfragment).commit();
                    getSupportFragmentManager().beginTransaction().remove(mfriendfragment).commit();
                    if (!mnoticefragment.isAdded()) {
                        getSupportFragmentManager().beginTransaction().add(R.id.google_map, mnoticefragment).commit();
                    }
                    break;
            }
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            //getSupportFragmentManager().beginTransaction().add(R.id.google_map, selectedFragment).commit();
            return true;
        }
    };
    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch ( id ){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    private NavigationView.OnNavigationItemSelectedListener listenerset = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            switch (id) {
                case R.id.person:
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, personaledit.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "person", Toast.LENGTH_LONG).show();
                    break;
                case R.id.device:
                    Intent intent2 = new Intent();
                    intent2.setClass(MainActivity.this, device.class);
                    startActivity(intent2);
                    Toast.makeText(getApplicationContext(), "device", Toast.LENGTH_LONG).show();
                    break;
                case R.id.add:
                    Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_LONG).show();
                    break;
                case R.id.logout:
                    Toast.makeText(getApplicationContext(), "log", Toast.LENGTH_LONG).show();
                    break;
            }
            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
            drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        }
    };
    private void setDefaultFragment()
    { getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mhomefragment).commit(); }

    public void onStart()
    {
        super.onStart();
        //WebService呼叫必須用Thread包著
        Thread thread = new Thread(){
            public void run() {
                String line = ws_test2.select_devicelocation("1");
                if (line.equals("error")==false) {
                    String[] split_line = line.split("%");
                    LatLng latLng2 = new LatLng(Double.parseDouble(split_line[4]), Double.parseDouble(split_line[5]));
                    markerOptions2 = new MarkerOptions().position(latLng2).title("I Am Here.");
                }
            }
        };
        thread.start();
    }
}
