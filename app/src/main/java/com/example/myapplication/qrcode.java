package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class qrcode extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private ImageView imageView9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);


        RelativeLayout rl = (RelativeLayout) findViewById(R.id.relative_scan_take_single);
        mScannerView = new ZXingScannerView(this);
        rl.addView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        mScannerView.setSquareViewFinder(true);
        mScannerView.setLaserEnabled(false);
        //mScannerView.setSoundEffectsEnabled(true);
        //mScannerView.setAutoFocus(true);

        imageView9 = (ImageView) findViewById(R.id.imageView9);
        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gohome = new Intent(qrcode.this,MainActivity.class);
                startActivity(gohome);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }


    @Override
    public void handleResult(Result rawResult) {
        Log.w("handleResult", rawResult.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(rawResult.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

}