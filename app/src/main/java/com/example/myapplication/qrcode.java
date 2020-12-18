package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.zxing.Result;

import de.hdodenhof.circleimageview.CircleImageView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class qrcode extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private ImageView imageView9;
    private Button qr, qrgenerate;
    private CircleImageView userpicture;
    byte[] byteArray;
    ImageView qrimg;
    private String line=null, line2=null;
    Handler handler;
    GoogleSignInAccount alreadyloggedAccount;
    TextView email,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        //mScannerView.setSoundEffectsEnabled(true);
        //mScannerView.setAutoFocus(true);

        imageView9 = (ImageView) findViewById(R.id.imageView9);
        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.relative_scan_take_single);
        mScannerView = new ZXingScannerView(qrcode.this);
        rl.addView(mScannerView);
        mScannerView.setResultHandler(qrcode.this);
        mScannerView.startCamera();
        mScannerView.setSquareViewFinder(true);
        mScannerView.setLaserEnabled(false);
        qr=(Button) findViewById(R.id.button7);
        qrgenerate=(Button) findViewById(R.id.button8);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qr.setTextColor(Color.parseColor("#000000"));
                qrgenerate.setTextColor(Color.parseColor("#707070"));
                RelativeLayout rl = (RelativeLayout) findViewById(R.id.relative_scan_take_single);
                mScannerView = new ZXingScannerView(qrcode.this);
                rl.addView(mScannerView);
                mScannerView.setResultHandler(qrcode.this);
                mScannerView.startCamera();
                mScannerView.setSquareViewFinder(true);
                mScannerView.setLaserEnabled(false);
            }
        });
        qrgenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrgenerate.setTextColor(Color.parseColor("#000000"));
                qr.setTextColor(Color.parseColor("#707070"));
                generate();
            }
        });
        handler = new Handler();
        alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
    @Override
    public void handleResult(final Result rawResult) {
        Log.w("handleResult", rawResult.getText());
        Thread thread = new Thread() {
            public void run() {
                line2 = ws_test2.personinfoselect(rawResult.getText());//googleSignInAccount.getEmail()
                handler.post(runnableUi2);
            }
        };
        thread.start();
    }
    private void generate ()
    {
        Thread thread = new Thread() {
            public void run() {
                line = ws_test2.personinfoselect("Apple@gmail.com");//googleSignInAccount.getEmail()
                handler.post(runnableUi);
            }
        };
        thread.start();
    }
    Runnable runnableUi = new Runnable()
    {
        @Override
        public void run() {
            if (line.equals("error")==false) {
                String[] split_line = line.split("%");
                RelativeLayout r2 = (RelativeLayout) findViewById(R.id.relative_scan_take_single);
                qrimg = new ImageView(qrcode.this);
                byteArray = Base64.decode(split_line[7], Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                qrimg.setImageBitmap(Bitmap.createScaledBitmap(decodedImage, r2.getWidth(), r2.getHeight()-10, false));
                r2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                r2.addView(qrimg);
            }
        }
    };
    Runnable runnableUi2 = new Runnable()
    {
        @Override
        public void run() {
            if (line2.equals("error")==false) {
                String[] split_line = line2.split("%");
                String addname = (split_line[0].replaceAll("\\s",""));
                String addemail = (split_line[1].replaceAll("\\s",""));
                byteArray = Base64.decode(split_line[6], Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                showDialog(addname,addemail,decodedImage);
            }
        }
    };
    private void showDialog(String resultname, String resultemail, Bitmap resultpicture){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(qrcode.this);
        View view = LayoutInflater.from(qrcode.this).inflate(R.layout.activity_addfriend2,(LinearLayout) findViewById(R.id.addfriendcheck1));
        normalDialog.setView(view);
        normalDialog.setCancelable(false);
        email=view.findViewById(R.id.tvemail);
        name=view.findViewById(R.id.tvname);
        userpicture=view.findViewById(R.id.userpictureadd);
        name.setText(resultname);
        email.setText(resultemail);
        userpicture.setImageBitmap(resultpicture);
        TextView cancel = (TextView)view.findViewById(R.id.canceladdfriendqr);
        TextView add = (TextView)view.findViewById(R.id.addfriendqr);
        final AlertDialog alertDialog = normalDialog.create();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(){
                    public void run() {
                        String line = ws_test2.addfriend(alreadyloggedAccount.getEmail(),email.toString(),0,alreadyloggedAccount.getEmail());
                        if (line.equals("1")==true) {

                        }
                    }
                };
                thread.start();
                alertDialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                mScannerView.startCamera();
            }
        });
        alertDialog.show();
    }
}