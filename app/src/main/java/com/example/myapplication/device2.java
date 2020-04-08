package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class device2 extends AppCompatActivity {

    private Context context;
    pop_window_view mwindow;
    TextView datepick;
    EditText meditText;
    DatePickerDialog.OnDateSetListener setListener;
    private CircleImageView circleimageview;
    public static final String TAG="MyLog";
    private static final  int REQUEST_CAMERA_CAPTURE=1;
    private static final  int REQUEST_IMAGE_CAPTURE=2;
    private File imagePath;
    private ImageView imageView6,imageView9;
    private RelativeLayout layout1,layout2;
    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
    String startText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device2);

        datepick = findViewById(R.id.textView);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        datepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        device2.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
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
                Intent godevice = new Intent(device2.this,device.class);
                startActivity(godevice);
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
                mwindow = new pop_window_view(device2.this, itemsOnClick);
                mwindow.showAtLocation(device2.this.findViewById(R.id.person1), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
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
    //private void takePhoto()
    //{
    //   if(hasSdcard())
    //  {
    //Environment.DIRECTORY_PICTURES{};
    //     Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES);
    //    if (!imagePath.exists())
    //   {imagePath.mkdirs();}
    //  imagePath = new File(imagePath.getPath() + "/" + "temp.png");
    //  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    //  if (intent.resolveActivity(getPackageManager()) != null)
    //  {
    //      intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagePath));
    //startActivityForResult(intent, REQUEST_CAMERA_CAPTURE);
    //  }
    //}
    //}

    private String takePicture = Environment.getExternalStorageDirectory().toString() + "/CAMERA";
    private String picturePath;
    protected void takePhoto() {
        imagePath = new File(takePicture, System.currentTimeMillis() + ".jpg");
        if (!imagePath.exists()) {
            File vDirPath = imagePath.getParentFile();
            if (!vDirPath.exists())
            { vDirPath.mkdirs(); }
        }
        if (hasSdcard()) {
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
        }
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

    private boolean hasSdcard()
    {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
        {return true;}
        else
        {return false;}
    }
}
