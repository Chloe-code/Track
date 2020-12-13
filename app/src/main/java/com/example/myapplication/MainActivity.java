package com.example.myapplication;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import android.os.Handler;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Double.parseDouble;

public class MainActivity extends AppCompatActivity implements BeaconConsumer {
    //implements OnMapReadyCallback
    Toolbar toolbar;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    DrawerLayout drawerLayout;
    ImageButton menuRight;
    private Fragment mhomefragment=null, mfriendfragment=null, mhistoryfragment=null, mnoticefragment=null;
    public MarkerOptions markerOptions2,markerOptions;
    ArrayList<LatLng> friendmarker = new ArrayList<>();
    ArrayList<String> infowindow = new ArrayList<>();
    String[] split_line10=null;
    String dline=null, gmail;
    mapinfowindow adapter;
    private Handler handler = new Handler();
    Geocoder geocoder;
    private GoogleSignInClient googleSignInClient;
    public LatLng latLng,latLng3;
    Integer standardrssi=0,nowrssi=0, sum=0, beaconfirstcheck=0;
    private BeaconManager beaconManager;
    private ArrayList<Integer> averagerssi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = getIntent();
        //gmail = intent.getStringExtra("gmail");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
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
        Bundle bundle = new Bundle();
        bundle.putString("gmail", gmail);
        Log.v("test1","Main bundle : "+gmail);
        mhomefragment.setArguments(bundle);
        mfriendfragment=new FriendFragment();
        mhistoryfragment=new HistoryFragment();
        mnoticefragment=new NoticeFragment();
        setDefaultFragment();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("beacon_open"));
        beaconManager = BeaconManager.getInstanceForApplication(getApplication());
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
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
                            latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            markerOptions = new MarkerOptions().position(latLng).title("I Am Here.").icon(bitmapDescriptorFromVector(MainActivity.this,R.drawable.ic_user));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
                            //googleMap.setMyLocationEnabled(true);
                            googleMap.addMarker(markerOptions).hideInfoWindow();
                            //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            googleMap.addMarker(markerOptions2);
                            for(int i=0;i<friendmarker.size(); i++)
                            {
                                Log.v("test3","12/8  "+friendmarker.get(i));
                                googleMap.addMarker(new MarkerOptions().position(friendmarker.get(i)).title(split_line10[6]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                            }
                            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(final Marker marker) {
                                    String m = marker.getTitle();
                                    infowindow = new ArrayList<>();
                                    onmarker(m,googleMap,marker);
                                    int zoom = (int)googleMap.getCameraPosition().zoom;
                                    CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new
                                            LatLng(marker.getPosition().latitude + (double)200/Math.pow(2, zoom),
                                            marker.getPosition().longitude), zoom);
                                    googleMap.animateCamera(cu,500,null);
                                    return true;
                                }
                            });
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
                    getSupportFragmentManager().beginTransaction().hide(mfriendfragment).commit();
                    getSupportFragmentManager().beginTransaction().hide(mhistoryfragment).commit();
                    getSupportFragmentManager().beginTransaction().hide(mnoticefragment).commit();
                    if (!mhomefragment.isAdded()) {
                        getSupportFragmentManager().beginTransaction().add(R.id.google_map, mhomefragment).detach(mhomefragment).attach(mhomefragment).commit();  //.detach(mhomefragment).attach(mhomefragment)
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                googleMap.getUiSettings().setScrollGesturesEnabled(true);
                            }
                        });
                    }
                    else {
                        getSupportFragmentManager().beginTransaction().show(mhomefragment).detach(mhomefragment).attach(mhomefragment).commit();
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                googleMap.getUiSettings().setScrollGesturesEnabled(true);
                            }
                        });
                    }
                    break;
                case R.id.action_friend:
                    getSupportFragmentManager().beginTransaction().hide(mhomefragment).commit();
                    getSupportFragmentManager().beginTransaction().hide(mhistoryfragment).commit();
                    getSupportFragmentManager().beginTransaction().hide(mnoticefragment).commit();
                    if (!mfriendfragment.isAdded()) {
                        getSupportFragmentManager().beginTransaction().add(R.id.google_map, mfriendfragment).commit();
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                googleMap.getUiSettings().setScrollGesturesEnabled(false);
                            }
                        });
                    }
                    else {
                        getSupportFragmentManager().beginTransaction().show(mfriendfragment).commit();
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                googleMap.getUiSettings().setScrollGesturesEnabled(false);
                            }
                        });
                    }
                    break;
                case R.id.action_history:
                    getSupportFragmentManager().beginTransaction().hide(mhomefragment).commit();
                    getSupportFragmentManager().beginTransaction().hide(mfriendfragment).commit();
                    getSupportFragmentManager().beginTransaction().hide(mnoticefragment).commit();
                    if (!mhistoryfragment.isAdded()) {
                        getSupportFragmentManager().beginTransaction().add(R.id.google_map, mhistoryfragment).commit();
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                googleMap.getUiSettings().setScrollGesturesEnabled(false);
                            }
                        });
                    }
                    else {
                        getSupportFragmentManager().beginTransaction().show(mhistoryfragment).commit();
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                googleMap.getUiSettings().setScrollGesturesEnabled(false);
                            }
                        });
                    }
                    break;
                case R.id.action_notice:
                    getSupportFragmentManager().beginTransaction().hide(mhomefragment).commit();
                    getSupportFragmentManager().beginTransaction().hide(mhistoryfragment).commit();
                    getSupportFragmentManager().beginTransaction().hide(mfriendfragment).commit();
                    if (!mnoticefragment.isAdded()) {
                        getSupportFragmentManager().beginTransaction().add(R.id.google_map, mnoticefragment).detach(mnoticefragment).attach(mnoticefragment).commit();
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                googleMap.getUiSettings().setScrollGesturesEnabled(false);
                            }
                        });
                    }
                    else {
                        getSupportFragmentManager().beginTransaction().show(mnoticefragment).detach(mnoticefragment).attach(mnoticefragment).commit();
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                googleMap.getUiSettings().setScrollGesturesEnabled(false);
                            }
                        });
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
                    break;
                case R.id.device:
                    Intent intent2 = new Intent();
                    intent2.setClass(MainActivity.this, devicelist.class);
                    startActivity(intent2);
                    break;
                case R.id.add:
                    Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_LONG).show();
                    break;
                case R.id.beacon:
                    Intent intent4 = new Intent();
                    intent4.setClass(MainActivity.this, beaconopen.class);
                    startActivity(intent4);
                    break;
                case R.id.logout:
                    showDialog();
                    //Toast.makeText(getApplicationContext(), "log", Toast.LENGTH_LONG).show();
                    break;
            }
            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
            drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        }
    };
    private void showDialog(){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.logout,(LinearLayout) findViewById(R.id.loading));
        normalDialog.setView(view);
        normalDialog.setCancelable(false);
        TextView cancel = (TextView)view.findViewById(R.id.btnNegative);
        TextView logout = (TextView)view.findViewById(R.id.btnPositive);
        final AlertDialog alertDialog = normalDialog.create();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //On Succesfull signout we navigate the user back to LoginActivity
                        Intent intent=new Intent(MainActivity.this,Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        alertDialog.dismiss();
                        finish();
                    }
                });
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
        /*normalDialog.setPositiveButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });
        normalDialog.setNegativeButton("登出",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //On Succesfull signout we navigate the user back to LoginActivity
                                Intent intent=new Intent(MainActivity.this,Login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });
                    }
                });*/
    }
    private void setDefaultFragment()
    { getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mhomefragment).commit(); }

    public void onStart()
    {
        super.onStart();
        Timer timer = new Timer(true);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Thread thread = new Thread(){
                    public void run() {
                        String line = ws_test2.select_devicelocation("IM-235-TD001");
                        if (line.equals("error")==false) {
                            String[] split_line = line.split("%");
                            Log.v("test3","12/4  "+split_line.length);
                            LatLng latLng2 = new LatLng(parseDouble(split_line[4]), parseDouble(split_line[5]));
                            Log.v("test3","12/4  "+latLng2.toString());
                            markerOptions2 = new MarkerOptions().position(latLng2).title("IM-235-TD001");
                        }
                        infowindow = new ArrayList<>();
                        String[] line1 = ws_test2.homerecyclrview("Apple@gmail.com");
                        if (line1!=null) {
                            for (int i = 0; i < line1.length; i++) {
                                String devicelist = ws_test2.homerecyclrview2(line1[i]);
                                String line10 = ws_test2.select_devicelocation(devicelist);
                                if (line10.equals("error") == false) {
                                    split_line10 = line10.split("%");
                                    Log.v("test3","12/5  "+split_line10.length);
                                    LatLng latlngfrienddevice=new LatLng(Double.parseDouble(split_line10[4]), Double.parseDouble(split_line10[5]));
                                    Log.v("test3","12/5  "+latlngfrienddevice.toString());//確認藍色的有沒有出現
                                    friendmarker.add(latlngfrienddevice);
                                }
                            }
                        }
                    }
                };
                thread.start();
            }
        };
        timer.schedule(timerTask, 0,15000);
    }
    public void onmarker(final String m, final GoogleMap googleMap, final Marker marker)
    {
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if(msg.what == 1) {
                    if(infowindow.isEmpty())
                    { /*Toast.makeText(getApplication(),"沒有成功", Toast.LENGTH_SHORT);*/}
                    else
                    {
                        String address="NDHU1";
                        try {
                            geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                            List<Address> listaddress=geocoder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
                            if(listaddress==null||listaddress.size()==0)
                            { address="null"; }
                            else
                            {
                                Address addr=listaddress.get(0);
                                for(int i=0;i<=addr.getMaxAddressLineIndex();i++)
                                { address=addr.getAddressLine(i);}
                            }
                            infowindow.add(address);
                        } catch (IOException e) {
                            address="失敗"+e.toString();
                        }
                        adapter = new mapinfowindow(MainActivity.this,infowindow);
                        googleMap.setInfoWindowAdapter(adapter);
                        marker.showInfoWindow();
                    }
                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                dline = ws_test2.deviceinfoselect(m);
                if (dline.equals("error")==false) {
                    String[] split_dline = dline.split("%");
                    String dline2 = ws_test2.personinfoselect(split_dline[1]);
                    String[] split_dline2 = dline2.split("%");
                    infowindow.add(split_dline[0]);
                    infowindow.add(split_dline[3]);
                    if(split_dline[3].equals("女")==true)
                    { infowindow.add("女");}
                    else { infowindow.add("男");}
                    infowindow.add(split_dline2[0]);
                    infowindow.add(split_dline[6]);
                    infowindow.add(split_dline[7]);
                }
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }.start();
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes  int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_person_pin_circle_black_24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }//之後再考慮使用者本身要用什麼圖案標記在地圖上

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec + " Meter   " + meterInDec);
        Log.v("distance",String.valueOf(Radius*c*1000));
        return Radius * c*1000;
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String deviceuuid = intent.getStringExtra("device_beacon_open");
            final String devicedistance = intent.getStringExtra("device_distance");
            final Handler Handler = new Handler();
            Timer Timer = new Timer();
            TimerTask TimerTask = new TimerTask() {
                @Override
                public void run() {
                    new Thread() {
                        public void run() {
                            String line = ws_test2.deviceinfoselect(deviceuuid);
                            if (line.equals("error") == false) {
                                String[] split_line = line.split("%");
                                standardrssi = ws_test2.beaconrssi(split_line[5]+"M");
                            }
                        }
                    }.start();
                    Handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new  Thread(){
                                @Override
                                public void run() {
                                    int line = ws_test2.beaconcheck(deviceuuid);
                                    if (line==1) {
                                        Log.v("test2","測試1 stand"+standardrssi.toString());
                                        stopbeacon();
                                        beaconfirstcheck=1;
                                        Log.v("test2","測試2 now"+nowrssi.toString());
                                        if (standardrssi > nowrssi)
                                        {
                                            startService( new Intent( MainActivity.this, notification_device.class )) ;
                                            Log.v("test2","測試3");
                                        }
                                        startbeacon();
                                        Log.v("test2","測試4");
                                    }
                                    else if (line==0) {
                                        Log.v("test2","測試5");
                                        stopbeacon();
                                        stopService( new Intent( MainActivity.this, notification_device.class )) ;
                                    }
                                }
                            }.start();
                        }
                    },500);
                }
            };
            Timer.schedule(TimerTask, 5000, 50000);
            Toast.makeText(MainActivity.this, deviceuuid + " " + devicedistance, Toast.LENGTH_SHORT).show();
        }
    };
    /*@Override
    protected void onStop () {
        super .onStop() ;
        //startService( new Intent( this, notification_device.class )) ;
    }*/
    /*一開始寫近距離的偵測是用gps經緯度直接去計算public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String deviceuuid = intent.getStringExtra("device_beacon_open");
            final String devicedistance = intent.getStringExtra("device_distance");
            final Handler Handler = new Handler();
            Timer Timer = new Timer();
            TimerTask TimerTask = new TimerTask() {
                @Override
                public void run() {
                    new Thread() {
                        public void run() {
                            String line = ws_test2.select_devicelocation(deviceuuid);
                            if (line.equals("error") == false) {
                                String[] split_line = line.split("%");
                                latLng3 = new LatLng(parseDouble(split_line[4]), parseDouble(split_line[5]));
                            }
                        }
                    }.start();
                    Handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new  Thread(){
                                @Override
                                public void run() {
                                    int line = ws_test2.beaconcheck(deviceuuid);
                                    if (line==1) {
                                        Double distance_now = CalculationByDistance(latLng, latLng3);
                                        if (Integer.valueOf(distance_now.intValue()) > Integer.parseInt(devicedistance))
                                        {
                                            startService( new Intent( MainActivity.this, notification_device.class )) ;
                                        }
                                    }
                                    else if (line==0) {
                                        stopService( new Intent( MainActivity.this, notification_device.class )) ;
                                    }
                                }
                            }.start();
                        }
                    },500);
                }
            };
            Timer.schedule(TimerTask, 5000, 50000);
            Toast.makeText(MainActivity.this, deviceuuid + " " + devicedistance, Toast.LENGTH_SHORT).show();
        }
    };*/
    public void onBeaconServiceConnect() {

        final Region region = new Region("myBeaons",null, null, null);
        beaconManager.addMonitorNotifier(new MonitorNotifier() {

            @Override
            public void didEnterRegion(Region region) {
                System.out.println("ENTER ------------------->");
                try {
                    beaconManager.startRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didExitRegion(Region region) {
                System.out.println("EXIT----------------------->");
                try {
                    beaconManager.stopRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                try {
                    beaconManager.startRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                System.out.println( "I have just switched from seeing/not seeing beacons: "+state);
            }
        });

        beaconManager.addRangeNotifier(new RangeNotifier() {

            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

                if (beacons.size() > 0) {
                    for (Beacon b:beacons){
                        String uuid = String.valueOf(b.getId1());
                        if(uuid.equals("e2c56db5-dffb-48d2-b060-d0f5a71096e0")) {
                            if(uuid!=null) {
                                Log.v("test1","reviewbeacon有收到資料");
                                averagerssi.add(b.getRssi());
                            }
                        }
                    }
                }
                else if (beacons.size()==0)
                {    }
            }
        });
        try {
            //Tells the BeaconService to start looking for beacons that match the passed Region object.
            beaconManager.startMonitoringBeaconsInRegion(region);
        } catch (RemoteException e){    }
    }
    @Override
    public Context getApplicationContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        getApplication().unbindService(serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return getApplication().bindService(intent, serviceConnection, i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    public void startbeacon()
    {
        Log.v("test2","startbeacon");
        sum = 0;
        averagerssi = new ArrayList<>();
        beaconManager.bind(this);
    }
    public void stopbeacon()
    {
        Log.v("test2",beaconfirstcheck.toString());
        if(beaconfirstcheck==0)
        { nowrssi=0; }
        else
        {
            for(int i=0; i<averagerssi.size(); i++)
            { sum = sum + averagerssi.get(i); }
            nowrssi = sum/averagerssi.size();
        }
        beaconManager.unbind(this);
    }
}
