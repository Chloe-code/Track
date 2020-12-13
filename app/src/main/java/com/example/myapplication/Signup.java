package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.zxing.Result;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Signup extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    public static final String GOOGLE_ACCOUNT = "google_account";
    private TextView profileEmail, deviceid,userbirth,devicebirth,skip;
    private ViewFlipper viewFlipper;
    private ZXingScannerView mScannerView;
    private CircleImageView nextstep,userpicture,devicepicture;
    private EditText username,userphone,userintro,devicename,devicedistance,deviceintro;
    private ImageView usermale,userfemale,devicemale,devicefemale;
    byte[] byteArray;
    String encodedImage, gmail, usergender, devicegender;
    Handler handler;
    private String lineu=null, lineu2=null;
    DatePickerDialog.OnDateSetListener setListener,setListener2;
    //GoogleSignInAccount googleSignInAccount = getIntent().getExtras().getParcelable(GOOGLE_ACCOUNT);
    public GoogleSignInAccount googleSignInAccount;
    pop_window_view mwindow;
    private LinearLayout layout1;
    private ViewFlipper layout2;
    private Uri iconUri = null, cropImageUri = null;
    public static final String imageDirPath = "/sdcard/TrackDear";
    public static String crop_ImageName = "crop_image.jpg";
    private static final int REQUEST_CODE_TAKE_PHOTO = 0;
    private static final int REQUEST_CODE_CHOOSE_IMAGE = 1;
    private static final int REQUEST_CODE_CROP_IMAGE = 2;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Intent intent = getIntent();
        gmail = intent.getStringExtra("gmail");
        Log.v("test1","56"+gmail);
        profileEmail = findViewById(R.id.profile_email);
        deviceid = findViewById(R.id.deviceid);
        viewFlipper = findViewById(R.id.view_flipper);
        nextstep = findViewById(R.id.nextstep);
        userpicture = findViewById(R.id.adduserpicture);
        devicepicture = findViewById(R.id.adddevicepicture);
        username = (EditText) findViewById(R.id.insertusername);
        userphone = (EditText) findViewById(R.id.insertuserphone);
        userbirth = (TextView) findViewById(R.id.insertuserbirth);
        userintro = (EditText) findViewById(R.id.insertusernote);
        devicename = (EditText) findViewById(R.id.insertdevicename);
        devicedistance = (EditText) findViewById(R.id.insertdevicedistance);
        devicebirth = (TextView) findViewById(R.id.insertdevicebirth);
        deviceintro = (EditText) findViewById(R.id.insertdevicenote);
        usermale = (ImageView) findViewById(R.id.userviewmale);
        userfemale = (ImageView) findViewById(R.id.userviewfemale);
        devicemale = (ImageView) findViewById(R.id.deviceviewmale);
        devicefemale = (ImageView) findViewById(R.id.deviceviewfemale);
        skip = (TextView) findViewById(R.id.skip);
        handler = new Handler();
        layout1 = findViewById(R.id.signup);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });
        layout2 = findViewById(R.id.view_flipper);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        userbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Signup.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        devicebirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Signup.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener2,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = year+"/"+month+"/"+day;
                userbirth.setText(date);
            }
        };
        setListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = year+"/"+month+"/"+day;
                devicebirth.setText(date);
            }
        };
        userpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mwindow = new pop_window_view(Signup.this, itemsOnClick);
                mwindow.showAtLocation(Signup.this.findViewById(R.id.signup), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        devicepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mwindow = new pop_window_view(Signup.this, itemsOnClick);
                mwindow.showAtLocation(Signup.this.findViewById(R.id.signup), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        verifyStoragePermissions(this);
    }
    @Override
    public void handleResult(Result rawResult) {
        deviceid.setText(rawResult.getText());
    }
    public void nextstep(View v) {
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
        viewFlipper.showNext();
        if(viewFlipper.getCurrentView().getId()==R.id.matchdevice)
        {
            new Thread() {
                public void run() {
                    lineu = ws_test2.personinfoinsert(username.getText().toString(),gmail,userphone.getText().toString(),userbirth.getText().toString(),usergender,userintro.getText().toString(),encodedImage);
                    Log.v("test1","78"+lineu);
                    handler.post(runnableUi);
                }
            }.start();
            Log.v("test1","有沒有新增4");
            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplication(), finishsetup.class);
                    startActivity(intent);
                    finish();
                }
            });
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.relative_scan_take_single);
            mScannerView = new ZXingScannerView(this);
            rl.addView(mScannerView);
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
            mScannerView.setSquareViewFinder(true);
            mScannerView.setLaserEnabled(false);

        }
        else if(viewFlipper.getCurrentView().getId()==R.id.insertdevice)
        {
            Log.v("test1","有沒有新增3");
            nextstep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread() {
                        public void run() {
                            lineu2 = ws_test2.deviceinfoinsert(devicename.getText().toString(),gmail,deviceid.getText().toString(),devicebirth.getText().toString(),devicegender,devicedistance.getText().toString(),"null",deviceintro.getText().toString(),encodedImage);
                            Log.v("test1","7878"+lineu2);
                            handler.post(runnableUi2);
                        }
                    }.start();
                }
            });
        }
    }
    Runnable runnableUi = new Runnable()
    {
        @Override
        public void run() {
            if (lineu.equals("error")==false) {
                //Toast.makeText(this,"更新完成",Toast.LENGTH_SHORT).show();
            }
        }
    };
    Runnable runnableUi2 = new Runnable()
    {
        @Override
        public void run() {
            if (lineu2.equals("error")==false) {
                Intent intent = new Intent(getApplication(), finishsetup.class);
                startActivity(intent);
                finish();
            }
        }
    };
    public void usermale(View view)
    {
        usermale.setVisibility(View.VISIBLE);
        userfemale.setVisibility(View.INVISIBLE);
        usergender = "男";
    }
    public void userfemale(View view)
    {
        usermale.setVisibility(View.INVISIBLE);
        userfemale.setVisibility(View.VISIBLE);
        usergender = "女";
    }
    public void devicemale(View view)
    {
        devicemale.setVisibility(View.VISIBLE);
        devicefemale.setVisibility(View.INVISIBLE);
        devicegender = "男";
    }
    public void devicefemale(View view)
    {
        devicemale.setVisibility(View.INVISIBLE);
        devicefemale.setVisibility(View.VISIBLE);
        devicegender = "女";
    }
    private View.OnClickListener itemsOnClick = new View.OnClickListener()
    {
        public void onClick(View v) {
            mwindow.dismiss();
            switch (v.getId()) {
                case R.id.icon_btn_camera:
                    File dir = new File(imageDirPath);
                    if(!dir.exists()){
                        dir.mkdirs();
                    }
                    String fileName = System.currentTimeMillis() + ".jpg";
                    File file = new File(dir,fileName);
                    Log.e("相機存放圖片地址",file.getAbsolutePath());
                    iconUri = provider.getUriForFile(Signup.this,file);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, iconUri);
                    startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
                    break;
                case R.id.icon_btn_choose:
                    Intent intent2 = new Intent(Intent.ACTION_PICK, null);
                    intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent2, REQUEST_CODE_CHOOSE_IMAGE);
                    break;
                default:
                    break;
            }
        }
    };
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(Signup.this,imageDirPath,Toast.LENGTH_SHORT).show();
            return;
        }
        switch (requestCode){
            case REQUEST_CODE_TAKE_PHOTO:
                if(null != iconUri){
                    Intent it = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,iconUri);
                    sendBroadcast(it);
                    File file = new File(iconUri.getPath());
                    Log.e("文件是否存在",file.exists()+"");
                    if (file.exists()) {
                        startCropImage(iconUri);
                    }
                }
                break;
            case REQUEST_CODE_CHOOSE_IMAGE:
                Log.e("相簿選擇",data.getData()+"");
                if (data.getData() != null) {
                    iconUri = data.getData();
                    startCropImage(iconUri);
                }
                break;
            case REQUEST_CODE_CROP_IMAGE:
                if(null !=cropImageUri){
                    Log.e("剪切圖片地址",cropImageUri.getPath());
                    Bitmap bitmap = BitmapFactory.decodeFile(imageDirPath+File.separator+crop_ImageName);
                    Log.e("剪切圖片地址2",crop_ImageName);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    if(viewFlipper.getCurrentView().getId()==R.id.insertuser)
                    {
                        userpicture.setImageBitmap(bitmap);
                    }
                    if(viewFlipper.getCurrentView().getId()==R.id.insertdevice)
                    {
                        devicepicture.setImageBitmap(bitmap);
                    }
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                    byteArray = stream.toByteArray();
                    encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    bitmap.recycle();
                }
                break;
        }
    }
    public void startCropImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        File dir = new File(imageDirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        crop_ImageName = System.currentTimeMillis() + ".jpg";
        File crop_image = new File(dir,crop_ImageName);
        Log.e("相機存放圖片地址",crop_image.getAbsolutePath());
        cropImageUri = provider.getUriForFile(Signup.this,crop_image);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", false);
        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }
    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }
}
