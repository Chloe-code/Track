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
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.os.Environment.DIRECTORY_PICTURES;

public class personalsetting extends AppCompatActivity
{
    private Context context;
    pop_window_view mwindow;
    TextView datepick;
    EditText meditText;
    DatePickerDialog.OnDateSetListener setListener;

    private CircleImageView circleimageview;
    private Button buttonsave;
    public static final String TAG="MyLog";
    private static final  int REQUEST_CAMERA_CAPTURE=1;
    private static final  int REQUEST_IMAGE_CAPTURE=2;
    public static final int TAKE_PHOTO = 111;
    private File imagePath;
    private ImageView imageView6,imageView9;
    private ConstraintLayout layout1, layout2;
    //private RelativeLayout ;
    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
    String startText = "";
    private Uri uri;

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
                /*Intent gopersonaledit = new Intent(personalsetting.this,personaledit.class);
                startActivity(gopersonaledit);*/
            }
        });

        buttonsave = (Button) findViewById(R.id.buttonsave);
        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gopersonaledit = new Intent(personalsetting.this,personaledit.class);
                startActivity(gopersonaledit);
            }
        });
        layout1 = findViewById(R.id.person1);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });
        layout2 = findViewById(R.id.person2);
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
                mwindow.showAtLocation(personalsetting.this.findViewById(R.id.person1), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){

        public void onClick(View v) {
            mwindow.dismiss();
            switch (v.getId()) {
                case R.id.icon_btn_camera:
                    takePhoto();
                case R.id.icon_btn_choose:
                    choosePhoto();
                default:
                    break;
            }
        }
    };

    public void takePhoto(View view) {takePhoto();}
    public void choosePhoto(View view){choosePhoto();}
    public void myCamera(View view){myCamera(view);}

    private String takePicture = Environment.getExternalStorageDirectory().toString()+ "moon/images" ;
            //getExternalStorageDirectory(
    private String picturePath = "take_photo.jpg";
    /*public void takePhoto() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (!imagePath.exists()) {
            imagePath.mkdirs();
        }
        imagePath = new File(takePicture, "/temp.jpg");
        picturePath = takePicture + "/temp.jpg";
        if (imagePath != null) {
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagePath));
            startActivityForResult(captureIntent, TAKE_PHOTO);
        }
    }*/


    /*private void takePhoto()
    {
        if(hasSdcard())
        { Environment.DIRECTORY_PICTURES };
            Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES);
            if (!imagePath.exists())
            {imagePath.mkdirs();}
            imagePath = new File(imagePath.getPath() + "/" + "temp.png");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null)
            {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagePath));
                startActivityForResult(intent, REQUEST_CAMERA_CAPTURE);
            }
    }*/

    /*private void takePhoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }*/


    private void takePhoto() {
        //File imagePath = new File(takePicture, System.currentTimeMillis() + ".jpg");
        File vDirPath = new File(takePicture);
        if (!vDirPath.exists()) {
            //File vDirPath = imagePath.getParentFile();
            //if (!vDirPath.exists())
            { vDirPath.mkdirs(); }
        }
        File file = new File(vDirPath,takePicture);//指定拍照后相片保存地址，以覆盖方式保存。
        Log.e("相機存放圖片地址",file.getAbsolutePath());
        uri = Uri.fromFile(file);
        // 启动相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 设置输出路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CAMERA_CAPTURE);
        /*if (hasSdcard()) {
            try {
                picturePath = imagePath.getPath();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagePath));
                startActivityForResult(intent, REQUEST_CAMERA_CAPTURE);
            } catch (Exception e) {
                Toast.makeText(this, "未找到系統相機程式", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "沒有找的記憶體卡", Toast.LENGTH_SHORT).show();
        }*/
    }

    private void choosePhoto()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if(intent.resolveActivity(getPackageManager()) != null)
        {startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CAMERA_CAPTURE&&resultCode==RESULT_OK){
            FileInputStream fis = null;
            try
            {
                fis=new FileInputStream(imagePath);
                Bitmap bitmap= BitmapFactory.decodeStream(fis);
                circleimageview.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {e.printStackTrace();}
            finally
            {
                try{fis.close();} catch (IOException e) {e.printStackTrace();}
            }
        }
        else if(requestCode==REQUEST_IMAGE_CAPTURE&&resultCode==RESULT_OK)
        {
            Uri uri=data.getData();
            circleimageview.setImageURI(uri);
        }
    }
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == TAKE_PHOTO) {
                FileInputStream fis = null;
                try
                {
                    fis=new FileInputStream(imagePath);
                    Bitmap bitmap= BitmapFactory.decodeStream(fis);
                    circleimageview.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {e.printStackTrace();}
                finally
                {
                    try{fis.close();} catch (IOException e) {e.printStackTrace();}
                }
            }
        }

    }*/

    private boolean hasSdcard()
    {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
        {return true;}
        else
        {return false;}
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("確定退出 ?");
            builder.setMessage("現在返回"+"/n"+"已變更的資料將不會儲存");
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

}
