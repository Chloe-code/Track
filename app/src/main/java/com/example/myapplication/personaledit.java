package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import de.hdodenhof.circleimageview.CircleImageView;


public class personaledit extends AppCompatActivity
{
    private Context context;
    private Button button6;
    private ImageView imageView9,imagegender;
    private TextView name,phone,email,birth,intro;
    private String line=null;
    Handler handler;
    private CircleImageView circleimageview;
    byte[] byteArray;
    GoogleSignInAccount alreadyloggedAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaledit);
        imageView9 = (ImageView) findViewById(R.id.imageView9);
        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gohome = new Intent(personaledit.this, MainActivity.class);
                startActivity(gohome);
            }
        });
        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gopersonalsetting = new Intent(personaledit.this, personalsetting.class);
                startActivity(gopersonalsetting);
            }
        });
        context = this;
        name = (TextView) findViewById(R.id.textView6);
        email = (TextView) findViewById(R.id.textViewemail);
        phone = (TextView) findViewById(R.id.textView7);
        birth = (TextView) findViewById(R.id.textView8);
        intro = (TextView) findViewById(R.id.textView9);
        imagegender = (ImageView) findViewById(R.id.imageView8);
        circleimageview = (CircleImageView) findViewById(R.id.profile_image);
        handler = new Handler();
        alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(context);
    }
    protected void onStart()
    {
        super.onStart();
        new Thread() {
            public void run() {
                line = ws_test2.personinfoselect(alreadyloggedAccount.getEmail());//alreadyloggedAccount.getEmail()
                handler.post(runnableUi);
            }
        }.start();
    }
    Runnable runnableUi = new Runnable()
    {
        @Override
        public void run() {
            if (line.equals("error")==false) {
                String[] split_line = line.split("%");
                //更新介面
                name.setText(split_line[0]);
                email.setText(split_line[1]);
                phone.setText(split_line[2]);
                birth.setText(split_line[3]);
                if(split_line[4].equals("女")==true)
                { imagegender.setBackgroundResource(R.drawable.ic_female); }
                else { imagegender.setBackgroundResource(R.drawable.ic_male);}
                intro.setText(split_line[5]);
                byteArray = Base64.decode(split_line[6], Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                circleimageview.setImageBitmap(decodedImage);
            }
            else { name.setText("錯誤名稱!"); }
        }
    };

}
