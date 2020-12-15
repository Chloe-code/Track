package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.os.Environment.DIRECTORY_PICTURES;
import static java.lang.Thread.sleep;

public class personalsetting extends AppCompatActivity
{
    private Context context;
    pop_window_view mwindow;
    DatePickerDialog.OnDateSetListener setListener;
    private Button buttonsave;
    private CircleImageView circleimageview;
    public static final String TAG="MyLog";
    private ImageView imageView6,imageView9,male,female;
    private LinearLayout layout1,layout2;
    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
    String startText = "", encodedImage;
    private Uri iconUri = null, cropImageUri = null;
    public static final String imageDirPath = "/sdcard/TrackDear";
    public static final String crop_ImageName = "crop_image.jpg";
    private static final int REQUEST_CODE_TAKE_PHOTO = 0;
    private static final int REQUEST_CODE_CHOOSE_IMAGE = 1;
    private static final int REQUEST_CODE_CROP_IMAGE = 2;
    private EditText name,phone,email,intro,meditText;
    private TextView birth, datepick;
    byte[] byteArray;
    Handler handler;
    private String lineu=null, line2=null;
    GoogleSignInAccount alreadyloggedAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalsetting);
        datepick = findViewById(R.id.textView);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        datepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        personalsetting.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = year+"/"+month+"/"+day;
                datepick.setText(date);
            }
        };

        final SpannableStringBuilder firstStringBuilder = new SpannableStringBuilder(startText);

        StyleSpan firstStyleSpan = new StyleSpan(Typeface.NORMAL);

        firstStringBuilder.setSpan(firstStyleSpan, 0, firstStringBuilder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        meditText = findViewById(R.id.editText10);
        spannableStringBuilder.append(firstStringBuilder);
        spannableStringBuilder.append("");

        meditText.setText(spannableStringBuilder);
        Selection.setSelection(meditText.getText(), startText.length() );

        meditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            { // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().startsWith(firstStringBuilder.toString()) )
                {
                    Log.e("StringBuilder_TAG", spannableStringBuilder.toString());
                    meditText.setText(spannableStringBuilder);
                    Selection.setSelection(meditText.getText(), meditText.getText().length() );
                }
                else
                {
                    spannableStringBuilder.clear();
                    spannableStringBuilder.append(s.toString());
                    Log.e("My_TAG", spannableStringBuilder.toString());
                }
            }
        });

    circleimageview=(CircleImageView) findViewById(R.id.profile_image);
        imageView9 = (ImageView) findViewById(R.id.imageView9);
        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.imageView9){
                    new AlertDialog.Builder(personalsetting.this)
                            .setTitle("確定退出")
                            .setMessage("現在返回已變更的資料將不會儲存")
                            .setNegativeButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                { finish(); }
                            })
                            .setPositiveButton("取消",null)
                            .show();
                }
            }
        });

        buttonsave = (Button) findViewById(R.id.buttonsave);
        layout1 = findViewById(R.id.person3);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });
        layout2 = findViewById(R.id.person4);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });

        EditText editText7 = (EditText)findViewById(R.id.editText7);
        editText7.setSelection(editText7.getText().length());

        context=this;
        imageView6=(ImageView)findViewById(R.id.imageView6);
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mwindow = new pop_window_view(personalsetting.this, itemsOnClick);
                mwindow.showAtLocation(personalsetting.this.findViewById(R.id.person3), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        name = (EditText) findViewById(R.id.editText7);
        email = (EditText) findViewById(R.id.editText11);
        phone = (EditText) findViewById(R.id.editText8);
        birth = (TextView) findViewById(R.id.textView);
        intro = (EditText) findViewById(R.id.editText10);
        male = (ImageView) findViewById(R.id.imageviewmale);
        female = (ImageView) findViewById(R.id.imageviewfemale);
        alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(context);
        handler = new Handler();
        new Thread() {
            public void run() {
                line2 = ws_test2.personinfoselect(alreadyloggedAccount.getEmail());//alreadyloggedAccount.getEmail()
                handler.post(runnableUi2);
            }
        }.start();
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
                    iconUri = provider.getUriForFile(personalsetting.this,file);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(personalsetting.this,imageDirPath,Toast.LENGTH_SHORT).show();
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
                Toast.makeText(personalsetting.this,"剪切完畢",Toast.LENGTH_SHORT).show();
                if(null !=cropImageUri){
                    Log.e("剪切圖片地址",cropImageUri.getPath());
                    Bitmap bitmap = BitmapFactory.decodeFile(imageDirPath+File.separator+crop_ImageName);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    circleimageview.setImageBitmap(bitmap);
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
        File crop_image = new File(dir,crop_ImageName);
        cropImageUri = provider.getUriForFile(personalsetting.this,crop_image);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", false);
        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("確定退出 ?");
            builder.setMessage("現在返回"+"/n已變更的資料將不會儲存");
            builder.setNegativeButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                { finish(); }
            });
            builder.setPositiveButton("取消",null);
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }
    public void maleclick(View view)
    {
        male.setVisibility(View.VISIBLE);
        female.setVisibility(View.INVISIBLE);
    }
    public void femaleclick(View view)
    {
        male.setVisibility(View.INVISIBLE);
        female.setVisibility(View.VISIBLE);
    }
    public void update(View view)
    {
        new Thread() {
            public void run() {
                if(male.getVisibility()==View.VISIBLE)
                {
                    Log.v("test1","set"+encodedImage);
                    lineu = ws_test2.personinfoupdate(name.getText().toString(),email.getText().toString(),phone.getText().toString(),birth.getText().toString(),"男",intro.getText().toString(),encodedImage);
                }
                if(female.getVisibility()==View.VISIBLE)
                {
                    Log.v("test1","set"+encodedImage);
                    lineu = ws_test2.personinfoupdate(name.getText().toString(),email.getText().toString(),phone.getText().toString(),birth.getText().toString(),"女",intro.getText().toString(),encodedImage);
                }
                handler.post(runnableUi);
            }
        }.start();
    }

    Runnable runnableUi = new Runnable()
    {
        @Override
        public void run() {
            if (lineu.equals("error")==false) {
                //Toast.makeText(this,"更新完成",Toast.LENGTH_SHORT).show();
                personalsetting.this.finish();
            }
        }
    };
    Runnable runnableUi2 = new Runnable()
    {
        @Override
        public void run() {
            if (line2.equals("error")==false) {
                String[] split_line2 = line2.split("%");
                name.setText(split_line2[0]);
                email.setText(split_line2[1]);
                phone.setText(split_line2[2]);
                birth.setText(split_line2[3]);
                if(split_line2[4].equals("女")==true)
                { female.setVisibility(View.VISIBLE);}
                else { male.setVisibility(View.VISIBLE);}
                intro.setText(split_line2[5]);
                byteArray = Base64.decode(split_line2[6], Base64.DEFAULT);
                encodedImage = split_line2[6];
                Log.v("test1","set1122"+encodedImage);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                circleimageview.setImageBitmap(decodedImage);
            }
        }
    };

}
